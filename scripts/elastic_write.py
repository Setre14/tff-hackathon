from elasticsearch import Elasticsearch

# Connect to Elasticsearch
es = Elasticsearch(
    ['https://localhost:9200'],
    basic_auth=('elastic', 'Wh=h-WIPDyMNK2iX*N9='),
    verify_certs=False,
    ssl_show_warn=False
)

# Delete all documents in index
es.delete_by_query(index='my_index', body={'query': {'match_all': {}}})