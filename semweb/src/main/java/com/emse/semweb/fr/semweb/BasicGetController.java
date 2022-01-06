package com.emse.semweb.fr.semweb;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BasicGetController {

    List<String> resultsSubject = new ArrayList<String>();
    List<String> resultsObject = new ArrayList<String>();
    List<String> resultsPredicate = new ArrayList<String>();

    @RequestMapping(value = "/getValues", method = RequestMethod.GET, produces="application/json" )
    public String getValues(Model model) {
        String datasetName = "ds";
        String datasetURL = "http://localhost:3030/" + datasetName;
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlQuery = datasetURL + "/query";
        String graphStore = datasetURL + "/data";

        String queryOne = "SELECT ?subject ?predicate ?object WHERE {?subject ?predicate ?object} LIMIT 100";

        RDFConnection connection = RDFConnectionFactory.connect(sparqlEndpoint, sparqlQuery, graphStore);
        QueryExecution execution = connection.query(queryOne);
        ResultSet resultSet = execution.execSelect();
        while(resultSet.hasNext()) {
            QuerySolution solution = resultSet.next();
            Resource subject = solution.getResource("subject");
            Resource predicate = solution.getResource("predicate");
            // Literal object = solution.getLiteral("object");
            resultsSubject.add(subject.toString());
            resultsPredicate.add(predicate.toString());
            // resultsObject.add(object.toString());
        }

        model.addAttribute("subject", resultsSubject);
        model.addAttribute("predicate", resultsPredicate);
        // model.addAttribute("object", resultsObject);

        System.out.println(model);

        execution.abort();
        return "Values for subject, property and object stored to the list.";
    }

}
