package fr.emse.master;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;

public class App{

    static Model model = ModelFactory.createDefaultModel();

    final static String rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    final static String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
    final static String sosa = "http://www.w3.org/ns/sosa/";
    final static String om = "http://www.ontology-of-units-of-measure.org/resource/om-2/";
    final static String xsd = "http://www.w3.org/2001/XMLSchema#";
    final static String time = "http://www.w3.org/2006/time#";

    final static String territoire = "https://territoire.emse.fr/kg/ontology/";
    final static String seas = "https://w3id.org/seas/";
    final static String bot = "https://w3id.org/bot#";
    final static String qudt = "http://qudt.org/1.1/schema/qudt#";
    
    public static void storeToFuseki(Model model){
        String dataSetName = "ds";
        String datasetURL = "http://localhost:3030/" + dataSetName;
        String sparqlEndpoint = datasetURL + "/sparql";
        String sparqlUpdate = datasetURL + "/update";
        String graphStore = datasetURL + "/data";
        RDFConnection conneg = RDFConnectionFactory.connect(sparqlEndpoint,sparqlUpdate,graphStore);
        conneg.load(model); // add the content of model to the triplestore
        // conneg.update("INSERT DATA { <test> a <TestClass> }"); // add the triple to the triplestore
    }

    public static void createNode(String unit , String val , int value){
        
        Resource blank_node = model.createResource();
        Property p1 = model.createProperty(rdf + "type");
        Property p2 = model.createProperty(om + "Measure");
        blank_node.addProperty(p1 , p2);

        Property p3 = model.createProperty(om + "hasUnit");
        Property p4 = model.createProperty(unit);
        blank_node.addProperty(p3, p4);

        Property p5 = model.createProperty(om + "hasNumericalValue");
        Literal l = model.createTypedLiteral(val, xsd + "double");
        blank_node.addProperty(p5, l);
        
        model.add(model.createResource("Observation/" + value) , model.createProperty(sosa + "hasResult") , blank_node);
    }

    public static String convertDateTime(String timeStamp){


        
        //long epoch = 1636954288932760000L;
        //LocalDateTime currentDateTime = LocalDateTime.now();
        //LocalDateTime.ofEpochSecond(epochSecond, nanoOfSecond, offset)
        //System.out.println("Current Date and Time Without Formatting: " + currentDateTime);
        /*
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'hh:mm:ss");
        System.out.println("Current Date and Time: " + currentDateTime.format(format));
        */


        long epoch = (long) Double.parseDouble(timeStamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = sdf.format(new Date (epoch/1000000)); 
        return date;
    }
    public static void main(String[] args) {

        model.setNsPrefix("rdf", rdf);
        model.setNsPrefix("rdfs", rdfs);
        model.setNsPrefix("xsd", xsd);
        model.setNsPrefix("sosa", sosa);
        model.setNsPrefix("om", om);
        model.setNsPrefix("time" ,time);
        

        /*
        Property seas = model.createProperty("https://w3id.org/seas");
        Property bot = model.createProperty("https://w3id.org/bot#");
        Property rdfs = model.createProperty("http://www.w3.org/2000/01/rdf-schema#");
        Property territoire = model.createProperty("https://territoire.emse.fr/kg/ontology/");
        Property sosa = model.createProperty("http://www.w3.org/ns/sosa/");
        Property rdf = model.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        Property qudt = model.createProperty("http://qudt.org/1.1/schema/qudt#");
        Property om = model.createProperty("http://www.ontology-of-units-of-measure.org/resource/om-2/");
        Property xsd = model.createProperty("http://www.w3.org/2001/XMLSchema#");
        */

       String fileToRead = "sensor-measurement-one-day.csv";

       try{
           BufferedReader reader = new BufferedReader(new FileReader(fileToRead));
           reader.readLine();
           String line;
           int value = 0;
           
           while((line = reader.readLine()) != null){
                value = value + 1;
                if(value == 30)
                    break;
                if (line.isEmpty()) 
                    continue;
                
                String[] raw = line.split(",");
                HashMap<String , String> sensor = new HashMap<String,String>();

                sensor.put("name",raw[0]);
                sensor.put("time" , convertDateTime(raw[1]));
                sensor.put("humidity" , raw[2]);
                sensor.put("luminosity" , raw[3]);
                //We don't need Columns 4,5,6
                sensor.put("temperature" , raw[7]);
                sensor.put("id" , raw[8]);
                sensor.put("location",raw[9]);
                sensor.put("type" , raw[10]);

                Resource sosaResource = model.createResource("Observation/" + value);
                
                model.add(sosaResource, model.createProperty(rdf + "type"), model.createProperty(sosa + "Observation"));
                model.add(sosaResource, model.createProperty(sosa + "observedProperty"), model.createProperty("temperature"));
                model.add(sosaResource, model.createProperty(sosa + "observedProperty"), model.createProperty("humidity"));
                model.add(sosaResource, model.createProperty(sosa + "observedProperty"), model.createProperty("luminosity"));
                model.add(sosaResource, model.createProperty(sosa + "hasFeatureOfInterest"), model.createProperty(sensor.get("location")));
                model.add(sosaResource, model.createProperty(sosa + "madeBySensor"), model.createProperty("sensor/" + sensor.get("id")));
                
                if(!sensor.get("temperature").equals("")){
                    String tempUnit = om + "CelsiusTemperatureUnit";
                    createNode(tempUnit , sensor.get("temperature") , value);
                }
                if(!sensor.get("humidity").equals("")){
                    String humUnit = om + "gramPerKilogram";
                    createNode(humUnit, sensor.get("humidity") , value);
                }
                if(!sensor.get("luminosity").equals("")){
                    String lumUnit = om + "LuminousFluxUnit";
                    createNode(lumUnit , sensor.get("luminosity") , value);
                }
 
                //resultTime:
                Resource blank_node = model.createResource();

                Property q1 = model.createProperty(rdf + "type");
                Property q2 = model.createProperty(time + "Instant");
                blank_node.addProperty(q1 , q2);

                Property q3 = model.createProperty(time + "inXSDDateTimeStamp");
                Literal l = model.createTypedLiteral(sensor.get("time"), xsd + "dateTimeStamp");
                blank_node.addProperty(q3 , l);

                model.add(sosaResource, model.createProperty(sosa + "resultTime") , blank_node);
                
            }
            reader.close();
            
        }

        catch(Exception exception){
            exception.printStackTrace();
        }

        storeToFuseki(model);
        //System.out.println(model);
        model.write(System.out, "turtle");

        
    }
}
