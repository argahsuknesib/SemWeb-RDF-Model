package fr.emse.master.query;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class RDFQuery {
    public static void main(String[] args) {
        String datasetName = "ds";
        String datasetURL = "http://localhost:3030/" + datasetName;
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlQuery = datasetURL + "/query";
        String graphStore = datasetURL + "/data";
        String query = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object} LIMIT 100";
        RDFConnection connection = RDFConnectionFactory.connect(sparqlEndpoint, sparqlQuery, graphStore);
        QueryExecution execution = connection.query(query);
        ResultSet resultSet = execution.execSelect();
        while(resultSet.hasNext()){
            QuerySolution solution = resultSet.next();
            Resource subject = solution.getResource("subject");
            System.out.println("Subject is : " + subject);
        }
        execution.close();
    }
}
