package org.minjay.data.elasticsearch;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.collect.Maps;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by minjay on 2017/3/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ElasticsearchTest {

    private TransportClient client;

    @Before
    public void setup() {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "dev-es-cluster-minjay")
                .put("script.disable_dynamic", false)
                .build();
        this.client = new TransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
    }

    @Test
    public void test1() {
        GetResponse response = client
                .prepareGet("tendata_xinbee", "mail_delivery_task", "7a8ad8e3-8ce3-4e62-9406-bf5a3d190810")
                .setFields("name", "id")
                .execute().actionGet();
        System.out.println("a");
    }

    @Test
    public void test2() {
        ExplainResponse response = client
                .prepareExplain("minjay", "user", "AVr_zWN4Y83FxjU_W5Yi")
                .setQuery(QueryBuilders.termQuery("username", "minjay5"))
                .execute().actionGet();
        System.out.println("a");
    }
//生成数据测试方法
//    @Test
//    public void test2() {
//        UserDocument user = new UserDocument(1, "minjay", 25);
//        Map<String, Object> data = new HashedMap();
//        int flag = 3;
//        while(flag <= 1003) {
//            data.put("id", flag);
//            data.put("username", "minjay"+flag);
//            data.put("age", 30);
//            client.prepareIndex("minjay", "user").setSource(data).execute().actionGet();
//            flag+=1;
//        }
//    }

    @Test
    public void update() {
        Map<String, Object> params = Maps.newHashMap();
        params.put("username", "minjay26");
        UpdateResponse updateResponse = client.prepareUpdate("minjay", "user", "AVr5WhmweB8bKqMi0jto").
                setScript("ctx._source.username = username", ScriptService.ScriptType.INLINE)
                .setScriptParams(params)
                .execute().actionGet();
    }

    @Test
    public void test3() {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("username", "minjay")
                .operator(MatchQueryBuilder.Operator.AND)
                .zeroTermsQuery(MatchQueryBuilder.ZeroTermsQuery.ALL);

    }

    @Test
    public void test4() throws IOException {
        Map<String, Object> m = Maps.newHashMap();
        m.put("1", "Introduction");
        m.put("2", "Basics");
        m.put("3", "And the rest");
        XContentBuilder json = XContentFactory.jsonBuilder().prettyPrint()
                .startObject()
                .field("id").value("2123")
                .field("lastCommentTime", new Date())
                .nullField("published")
                .field("chapters").map(m)
                .field("title", "Mastering ElasticSearch")
                .array("tags", "search", "ElasticSearch", "nosql")
                .field("values")
                .startArray().value(1)
                .value(10)
                .endArray()
                .endObject();
        IndexRequestBuilder ib = client.prepareIndex("minjay", "book").setSource(
                json);
        IndexResponse response = ib.execute().actionGet();
    }

    @Test
    public void test5(){
        ClusterHealthResponse healthResponse = client.admin().cluster()
                .prepareHealth("minjay")
                .execute().actionGet();
        System.out.println("a");
    }

}
