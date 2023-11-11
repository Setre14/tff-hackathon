import requests
import os
import csv
from zipfile import ZipFile
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk

def upload_csv_to_elasticsearch(csv_file_path, index_name):
    with open(csv_file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        actions = [
            {
                "_index": index_name,
                "_source": row,
            }
            for row in reader
        ]
        bulk(es, actions)

url = 'https://owncloud.setre14.ddns.net/s/TcxrDUSQdJHWMtq/download?path=%2F&files=mastercard'

local_file_path = 'mastercard_download.zip'

response = requests.get(url, stream=True, verify=False)

if response.status_code == 200:
    with open(local_file_path, 'wb') as file:
        for chunk in response.iter_content(chunk_size=128):
            file.write(chunk)
    print("File downloaded successfully.")

    with ZipFile(local_file_path, "r") as root:
        ZipFile.extractall(root)

else:
    print(f"Failed to download file. Status code: {response.status_code}") 

# Connect to Elasticsearch
es = Elasticsearch(
    ['https://localhost:9200'],
    basic_auth=('elastic', 'Wh=h-WIPDyMNK2iX*N9='),
    verify_certs=False,
    ssl_show_warn=False
)

input('Press any key to continue ...')

# Process each CSV file in the extracted directory
for filename in os.listdir("extracted_folder"):
    if filename.endswith(".csv"):
        csv_file_path = os.path.join("extracted_folder", filename)
        index_name = filename.split(".")[0]  # Using filename as index name
        upload_csv_to_elasticsearch(csv_file_path, index_name)
        print(f"Uploaded {filename} to Elasticsearch")

# Clean up: Delete downloaded zip file and extracted directory
os.remove(local_file_path)
os.rmdir("extracted_folder")
