package com.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utils.EsClient;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

public class IdGetSearch {

    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client =  EsClient.getClient();
    String index = "sms-logs-index";
    String type="sms-logs-type";

    @Test
    public void findById() throws IOException {
        // 创建GetRequest对象
        GetRequest request = new GetRequest(index,type,"1");

        //  执行查询
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 输出结果
        System.out.println(response.getSourceAsMap());
    }

    @Test
    public  void findByIds() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.idsQuery().addIds("1","2","3"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public  void findByPrefix() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.prefixQuery("corpName","阿"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public  void findByFuzzy() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.fuzzyQuery("corpName","腾讯客堂").prefixLength(2));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public  void findByWildCard() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.wildcardQuery("corpName","海尔*"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public  void findByRang() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.rangeQuery("fee").gt(10).lte(30));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public  void findByRegexp() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.regexpQuery("mobile","138[0-9]{8}"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
