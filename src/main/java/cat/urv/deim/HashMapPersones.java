package cat.urv.deim;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import cat.urv.deim.exceptions.ElementNoTrobat;

public class HashMapPersones {
    private HashMapIndirecte<Integer, Persona> HashPersones;
    //private int mida;
    //private int nElem;


    //constructor
    public HashMapPersones(int mida){
        //this.mida=mida;
        //ArrayList <Node> taulaHash; //taula Hashing-> entre <> tipus de dades q utilitzo
        //x guardar elements -> part estatica de la taula amb una estructura del tipus ArrayList (java.util.ArrayList).
        //nElem=0;
        HashPersones = new HashMapIndirecte<>(mida);
    }

    /**
    * Constructor que crea una llista del tipus especificat i hi carrega totes les dades del fitxer
     * @param mida - tamany taula
     * @param filename - fitxer
    */
    public HashMapPersones(int mida, String filename) {
        HashPersones = new HashMapIndirecte<>(mida);
        carregarDades(filename,HashPersones);

    }

    /**
     * Mètode que carrega les dades del fitxer en el hashmap
     * @param filename
     */
    private void carregarDades(String filename, HashMapIndirecte<Integer,Persona> HashPersones ) {
        try{
            String linia;
            String[] trossos;
            Scanner fitxer = new Scanner(new File(filename));
            while(fitxer.hasNextLine()){
                try{
                    linia = fitxer.nextLine();
                    trossos = linia.split(",");
                    Persona p = new Persona(Integer.parseInt(trossos[0]), Integer.parseInt(trossos[1]), trossos[2], trossos[3], Integer.parseInt(trossos[4]), Integer.parseInt(trossos[5]));
                    this.inserir(p);

                }catch(NumberFormatException e){
                    System.out.println("Algun valor no es correcte ");
                }
            }
            fitxer.close();
        }catch(IOException e){
            System.out.println("Error en obrir el fitxer!");
        }
    }

    public void inserir(Persona p) {
        int key = p.getId_persona();
        HashPersones.inserir(key, p);
    }

/*
    public Persona[] elements(){
        Persona[] llistaPers = new Persona[HashPersones.numElements()];
        int i=0, j;
        Iterator<Persona> itePers= HashPersones.iterator();
        boolean trobat=false;

        while(itePers.hasNext()){
            Persona pers = itePers.next();
            j=i;
            while (!trobat && j!=0) {
                if(llistaPers[j-1].getId_persona()<pers.getId_persona()){
                trobat=true;
                }
                else{
                    llistaPers[j]=llistaPers[j-1];
                    j--;
                }
            }
            llistaPers[j]=pers;
            i++;
            if(trobat) trobat= false;
        }
        return llistaPers;
    }
*/

    public int[] obtenirIDs(){
        int[] identitats = new int[HashPersones.numElements()];
        int i = 0;
        Object[] claus = HashPersones.obtenirClaus();
        Integer[] claus2= new Integer[claus.length];
        System.arraycopy(claus, 0, claus2, 0, claus.length);
        for(int j=0; j<claus.length;j++){
            identitats[i]=claus2[j];
             i++;
        }
        return identitats;
     }


     public Persona[] personesPesInferior(int pes){
        LlistaPersones persPesInf = new LlistaPersones(true);
        //Persona[] persPesInf = new Persona[HashPersones.numElements()];
        //int i = 0;
        Object[] claus = HashPersones.obtenirClaus();
        Integer[] claus2= new Integer[claus.length];
        System.arraycopy(claus, 0, claus2, 0, claus.length);
            for(int j=0; j<claus.length;j++){
                try{
                if(HashPersones.consultar(claus2[j]).getPes()<pes){
                    persPesInf.inserir(consultar(claus2[j]));
                    //persPesInf[i]=(consultar(claus2[j]));
                    //i++;
                }
                }catch(ElementNoTrobat e){}
            }
        return persPesInf.elements();
    }
    /*


    public Persona[] personesPesInferior(int pes){
        Persona[] persPesInf = new Persona[HashPersones.numElements()];
        int i = 0;
        Object[] claus = HashPersones.obtenirClaus();
        Integer[] claus2= new Integer[claus.length];
        System.arraycopy(claus, 0, claus2, 0, claus.length);
        try{
            for(int j=0; j<claus.length;j++){
                if(consultar(claus2[j]).getPes()<pes){
                    persPesInf[i]=(consultar(claus2[j]));
                    i++;
                }
            }
        }catch(ElementNoTrobat e){}
        return persPesInf;


    public Persona[] personesPesInferior(int pes){
        Persona[] persPesInf = new Persona[HashPersones.numElements()];
        int i = 0;
        Integer[] claus = HashPersones.obtenirClaus();
        for(int j=0; j<claus.length;j++){
            try{
                while (HashPersones.consultar(j)!=null){
                        if(HashPersones.consultar(j).getPes()<pes){
                            persPesInf[i]=HashPersones.consultar(j);
                            i++;
                        }
                }
            }catch(ElementNoTrobat e){}
        }
        return persPesInf;

        Persona[] persones = elements();
        Persona[] persPesInf=new Persona[numElements()];
        int j=0;
        for(int i=0; i<numElements(); i++){
            if(persones[i].getPes()<pes){
                persPesInf[j]=persones[i];
                j++;
            }
        }
    }
    */

    // Metode per a obtenir un array amb tots els elements de K
    public Persona consultar(int key) throws ElementNoTrobat{
        return HashPersones.consultar(key);
    }


    // Metode per a esborrar un element de la taula de hash
    public void esborrar(int key) throws ElementNoTrobat{
        HashPersones.esborrar(key);
    }

    // Metode per a comprovar si un element esta a la taula de hash
    public boolean buscar(Persona id){
        int key= id.getId_persona();
        return HashPersones.buscar(key);
    }


    // Metode per a comprovar si la taula te elements
    public boolean esBuida(){
        return HashPersones.esBuida();
    }

    // Metode per a obtenir el nombre d'elements de la llista
    public int numElements(){
        return HashPersones.numElements();
    }

    // Metode per a obtenir les claus de la taula
    //public K[] obtenirClaus(){}

    // Metode per a saber el factor de carrega actual de la taula
    public float factorCarrega(){
        return HashPersones.factorCarrega();
    }

    // Metode per a saber la mida actual de la taula (la mida de la part estatica)
    public int mida(){
        return HashPersones.midaTaula();
    }
        /**
     * Mètode que et retorna totes les persones del Hashmap
     * @return Persona[]
*/
    public Persona[] elements(){
        Persona[] persones = new Persona[HashPersones.numElements()];
        int i = 0;
        Object[] claus = HashPersones.obtenirClaus();
        Integer[] claus2= new Integer[claus.length];
        System.arraycopy(claus, 0, claus2, 0, claus.length);
        try{
            for(int j=0; j<claus.length;j++){
                persones[i]=consultar(claus2[j]);
                i++;
            }
        }catch(ElementNoTrobat e){}

        return persones;
    }


    /**
     * Metode per a obtenir una persona a partir del seu id
     * @param id
     * @return Persona
     * @throws ElementNoTrobat
    */
    public Persona buscarPerId(int id) throws ElementNoTrobat{
        return HashPersones.consultar(id);
    }

    /**
     * Metode per a obtenir un array amb totes les persones que tenen un pes inferior al valor que es passa per parametre
     * @param pes
     * @return Persona[]

    public Persona[] personesPesInferior(int pes) {
        LlistaPersones llistaAux = new LlistaPersones(true);
        for (Node<Persona, Integer> n: taula){
            if(n.element.getPes()<pes ){
                llistaAux.inserir(n.element);
            }
        }
        return llistaAux.elements();
    }
    */

}
