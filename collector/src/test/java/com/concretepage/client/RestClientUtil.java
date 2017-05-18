package com.concretepage.client;

import com.concretepage.entity.Metric;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestClientUtil {

    public static void main(String args[]) {
        RestClientUtil util = new RestClientUtil();
        //util.getMetricByIdDemo();
        util.addMetricDemo();
        util.getAllMetricsDemo();
        //util.updateMetricDemo();
        //util.deleteMetricDemo();
    }

    public void getMetricByIdDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/metric/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Metric> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Metric.class, 1);
        Metric metric = responseEntity.getBody();
        System.out.println("Id:" + metric.getId() + ", Title:" + metric.getBeanName()
                + ", Category:" + metric.getBeanType());
    }

    public void getAllMetricsDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/metrics";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<Metric[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Metric[].class);
        Metric[] metrics = responseEntity.getBody();
        for (Metric metric : metrics) {
            System.out.println("Id:" + metric.getId() + ", Title:" + metric.getBeanName()
                    + ", Category: " + metric.getBeanType());
        }
    }

    public void addMetricDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/metric";
        Metric objMetric = new Metric();
        objMetric.setBeanName("Spring REST Security using Hibernate");
        objMetric.setBeanType("Spring");
        HttpEntity<Metric> requestEntity = new HttpEntity<Metric>(objMetric, headers);
        restTemplate.put(url, requestEntity);
     }

    public void updateMetricDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/metric";
        Metric objMetric = new Metric();
        objMetric.setBeanName("Update:Java Concurrency");
        objMetric.setBeanType("Java");
        HttpEntity<Metric> requestEntity = new HttpEntity<Metric>(objMetric, headers);
        restTemplate.put(url, requestEntity);
    }

    public void deleteMetricDemo() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/metric/{id}";
        HttpEntity<Metric> requestEntity = new HttpEntity<Metric>(headers);
        restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Void.class, 4);
    }
}
