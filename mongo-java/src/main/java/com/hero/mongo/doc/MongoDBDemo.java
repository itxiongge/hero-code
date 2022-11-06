package com.hero.mongo.doc;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class MongoDBDemo {
    //客户端
    private static MongoClient mongoClient;
    //数据库
    private static MongoDatabase database;
    //集合
    private static MongoCollection<Document> collection;

    static {
        mongoClient = new MongoClient("123.57.135.5", 27017);
        database = mongoClient.getDatabase("hero");
        collection = database.getCollection("employee");
    }

    public static void main(String[] args) {
        //docAdd();
        //docQueryAll();
        docQueryFilter();
        mongoClient.close();
    }
    //添加文档
    private static void docAdd() {
        Document doc1 = Document.parse("{name:'benson',city:'beijing',birth_day:new ISODate('2022-08-01'),expectSalary:18000}");
        Document doc2 = Document.parse("{name:'Vincent',city:'beijing',birth_day:new ISODate('1997-06-08'),expectSalary:102000}");
        collection.insertOne(doc1);
        collection.insertOne(doc2);
    }
    //文档查询
    private static void docQueryAll() {
        //查询所有，倒序排列
        FindIterable<Document> findIterable = collection
                .find()//查询所有
                .sort(Document.parse("{expectSalary:-1}"));//按expectSalary倒序
        for (Document document : findIterable) {
            System.out.println(document);
        }
    }
    //文档查询过滤
    private static void docQueryFilter() {
        //查询expectSalary大于21000的所有雇员，倒序排列
        FindIterable<Document> findIterable = collection
                .find(Filters.gt("expectSalary",21000))
                .sort(Document.parse("{expectSalary:-1}"));//按expectSalary倒序

        for (Document document : findIterable) {
            System.out.println(document);
        }
    }

}
