import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        String filename = "C:/Users/indra/Desktop/finalfinal";
        OntModel model = null;
        try {
            File file = new File(filename);
            FileReader reader = new FileReader(file);
            model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);

            model.read(reader, "RDF/XML");
            model.write(System.out, "RDF/XML-ABBREV");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Iterator classIter = model.listClasses();
                while(classIter.hasNext()){
                    OntClass ontClass = (OntClass) classIter.next();
                    String uri = ontClass.getURI();
                    if(uri!=null){
                        System.out.println(uri);
                }}
                String classURI= "http://www.semanticweb.org/indra/ontologies/2023/3/untitled-ontology-6#Pilot";
                OntClass pilot= model.getOntClass(classURI);
                System.out.println(pilot.getSuperClass());
                OntClass airCrew = pilot.getSuperClass();

                Iterator subIter = airCrew.listSubClasses();
                while(subIter.hasNext()){
                    OntClass sub = (OntClass) subIter.next();
                    System.out.println(sub);
                }
        String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                             "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                             "select ?uri " +
                             "where { " +
                             "?uri rdfs:subClassOf <http://www.semanticweb.org/indra/ontologies/2023/3/untitled-ontology-6#AirCrew> " +
                             "} \n";
        Query query = QueryFactory.create(queryString);

        System.out.println("-------------");

        System.out.println("Query Result Sheet1");

        System.out.println("-------------");

        QueryExecution qe = QueryExecutionFactory.create(query,model);
        ResultSet results = qe.execSelect();

        ResultSetFormatter.out(System.out,results,query);
        qe.close();

        String queryString2 = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "select * Where { " +
                "?Plane <http://www.semanticweb.org/indra/ontologies/2023/3/untitled-ontology-6#plane_id> ?x ." +
                "} \n";
        Query query2 = QueryFactory.create(queryString2);

        System.out.println("-------------");
        System.out.println("Query Result Sheet2");
        System.out.println("-------------");

        QueryExecution qe2 = QueryExecutionFactory.create(query2,model);
        try{

        ResultSet results2 = qe2.execSelect();
        while( results2.hasNext()) {
            QuerySolution soln = results2.nextSolution();
            Literal hasPoints = soln.getLiteral("x");
            System.out.println(hasPoints);
            }
        }finally {
            qe2.close();
        }

    }
}