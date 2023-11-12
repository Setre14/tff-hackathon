from elasticsearch import Elasticsearch

# Connect to Elasticsearch
es = Elasticsearch(
    ['http://localhost:9200'],
    verify_certs=False,
    ssl_show_warn=False
)

# Delete all documents in index
es.delete_by_query(index='my_index', body={'query': {'match_all': {}}})