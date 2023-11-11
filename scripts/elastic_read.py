from elasticsearch import Elasticsearch
import csv

# Connect to Elasticsearch
es = Elasticsearch(
    ['https://localhost:9200'],
    basic_auth=('elastic', 'Wh=h-WIPDyMNK2iX*N9='),
    verify_certs=False,
    ssl_show_warn=False
)

# Read data from CSV file
with open(r'C:\Users\HELLO\Downloads\temp\accommodations.csv', newline='') as csvfile:
    reader = csv.DictReader(csvfile)
    data = [row for row in reader]

# Index data into Elasticsearch
for row in data:
    es.index(index='my_index', body=row)