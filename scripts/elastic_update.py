import os
import csv
from zipfile import ZipFile
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk
import shutil

username = 'your_username'
password = 'your_password'

def get_first_row(csv_file_path):
    with open(csv_file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        first_row = next(reader, None)
        if first_row:
            return list(first_row.keys())[0], first_row
        return None, None

def upload_csv_to_elasticsearch(csv_file_path, index_name, es, document_id_field, first_row):
    if document_id_field is None:
        return  # Skip processing if document_id_field is not found

    with open(csv_file_path, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile)
        actions = [
            {
                "_op_type": "index",  # Change "_op_type" to "index" for updates
                "_index": index_name,
                "_id": row[document_id_field],  # Use the specified document identifier field
                "_source": first_row,
            }
            for row in reader
        ]
        bulk(es, actions)

def main():
    url = 'https://owncloud.setre14.ddns.net/s/TcxrDUSQdJHWMtq/download?path=%2F&files=data'
    data_dir = 'data'
    if not os.path.exists(data_dir):
        os.makedirs(data_dir)

    # Connect to Elasticsearch
    es = Elasticsearch(
        ['https://localhost:9200'],
        basic_auth=(username, password),
        verify_certs=False,
        ssl_show_warn=False
    )

    # Process each CSV file in the extracted directory
    for subdir in ['events', 'mastercard']:
        for filename in os.listdir(os.path.join(data_dir, subdir)):
            if filename.endswith(".csv"):
                csv_file_path = os.path.join(data_dir, subdir, filename)
                index_name = f'{subdir}-{filename.split(".")[0]}'  # Using filename as index name

                # Get the first key and row as the source
                document_id_field, first_row = get_first_row(csv_file_path)

                # Upload or update data to Elasticsearch
                try:
                    upload_csv_to_elasticsearch(csv_file_path, index_name, es, document_id_field, first_row)
                    print(f"Uploaded/Updated {filename} to Elasticsearch")
                except Exception as e:
                    print(f"Error processing {filename}: {str(e)}")

if __name__ == "__main__":
    main()
