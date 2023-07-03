# Lucene: long tests

Lucene is a mature open source full-text search engine library.

https://lucene.apache.org/

The Github repository is: https://github.com/apache/lucene

For this exercise, we have picked one test case from the Lucene project: TestWildcard. You can find it here:
https://github.com/apache/lucene/blob/main/lucene/core/src/test/org/apache/lucene/search/TestWildcard.java

The purpose of this test is to test the '*' and '?' wildcard characters in Lucene queries.

For this exercise, download this test to the org.apache.lucene.search package so that you can run it and refactor it.

## Background

Using the Lucene library, you can build and query a search index. You create 'documents' containing
e.g. labelled and typed fields and store these documents in an index. To retrieve documents, you build a
query from different query objects (alternatively you can parse a query string into query objects) and use this to
search the index. This will result in 0 or more documents found.

Lucene has the Directory abstraction in which search indexes are stored.
To add documents to an index, an IndexWriter is used.
To read from an index, an IndexReader is used. An IndexSearcher can run queries on an index using an IndexReader.

To build a query, a hierarchy of Query classes is available: for example TermQuery (to search based on a term)
and WildcardQuery (to search on text using wildcards). The WildcardQuery is the class under test here.

## Exercise

Do a quick walkthrough of this test class. What do you observe? What specifically makes this test hard(er)
to understand?

Focus on testAsterisk and testQuestionmark. Make one of the tests fail. What is the quality of the feedback the test
provides?

Refactor these tests into clean, readable, intention-revealing tests that give good feedback when they fail.
