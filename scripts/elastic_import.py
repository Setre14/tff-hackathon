import requests
import os
import csv
from zipfile import ZipFile
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk
import shutil

def upload_csv_to_elasticsearch(csv_file_path, index_name):
    if es.indices.exists(index=index_name):
        es.indices.delete(index=index_name)
    es.indices.create(
        index=index_name,
    )

    with open(csv_file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        actions = [
            {
                "_op_type": "create",
                "_index": index_name,
                "_source": row,
            }
            for row in reader
        ]
        bulk(es, actions)

url = 'https://owncloud.setre14.ddns.net/s/TcxrDUSQdJHWMtq/download?path=%2F&files=data'

tmp_dir = 'tmp'
if not os.path.exists(tmp_dir):
    os.makedirs(tmp_dir)

local_file_path = 'data.zip'
extracted_dir = os.path.join(tmp_dir, "extracted_dir", "data")

zip_path = os.path.join(tmp_dir, local_file_path)

response = requests.get(url, stream=True, verify=False)

if response.status_code == 200:
    with open(zip_path, 'wb') as file:
        for chunk in response.iter_content(chunk_size=128):
            file.write(chunk)
    print("File downloaded successfully.")

    with ZipFile(zip_path, "r") as root:
        ZipFile.extractall(root, path=os.path.join(tmp_dir, "extracted_dir"))

else:
    print(f"Failed to download file. Status code: {response.status_code}") 

# Connect to Elasticsearch
es = Elasticsearch(
    ['http://localhost:9200'],
    verify_certs=False,
    ssl_show_warn=False
)

# Process each CSV file in the extracted directory
for subdir in ['events', 'mastercard']:
    for filename in os.listdir(os.path.join(extracted_dir, subdir)):
        if filename.endswith(".csv"):
            csv_file_path = os.path.join(extracted_dir, subdir, filename)
            index_name = f'{subdir}-{filename.split(".")[0]}'  # Using filename as index name
            upload_csv_to_elasticsearch(csv_file_path, index_name)
            print(f"Uploaded {filename} to Elasticsearch")

# Clean up: Delete downloaded zip file and extracted directory
shutil.rmtree(tmp_dir)
