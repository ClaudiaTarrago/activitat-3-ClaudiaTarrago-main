package cat.urv.deim;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;

public class LlistaPersones {
    private boolean ordenada;
    private int longit;

    ILlistaGenerica<Persona> llista;

    // Constructor que crea una llista de persones buida del tipus especificat en el boolea (ordenada o no)
    public LlistaPersones(boolean ordenada) {
        if(ordenada){
            llista = new LlistaOrdenada<Persona>();
        }
        else{
            llista = new LlistaNoOrdenada<Persona>();
        }
        this.ordenada= ordenada;
        longit=0;
    }

    //Constructor que crea una llista del tipus especificat i hi carrega totes les dades del fitxer
    public LlistaPersones(boolean ordenada, String filename) {
        if(ordenada){
            llista = new LlistaOrdenada<Persona>();
        }
        else{
            llista = new LlistaNoOrdenada<Persona>();
        }
        this.ordenada= ordenada;
        this.longit=0;
        try{
            BufferedReader b = new BufferedReader(new FileReader(filename));
            String linia = b.readLine();
            while(linia !=null){

                    String[] dades = linia.split(",");
                    Persona persona = new Persona(Integer.parseInt(dades[0]), Integer.parseInt(dades[1]), dades[2], dades[3], Integer.parseInt(dades[4]), Integer.parseInt(dades[5]));
                    try{
                        llista.inserir(persona);

                    }catch(NumberFormatException e){
                    //cridem excepcio ja creada
                    }

                linia= b.readLine();
            }
            b.close();


        }catch(IOException e){
            e.printStackTrace();
        }
        //inserir a la pila

    }

    //Afegeix una nova persona a la llista que tenim inicialitzada
    public void inserir(Persona p) {
        llista.inserir(p);
        longit++;
    }

    //Metode per a consultar una persona a partir de la seva posicio
    public Persona consultar(int pos) throws PosicioForaRang {
            return llista.consultar(pos);
    }

    //Metode per a saber si una persona existeix a l'estructura
    public boolean existeix(Persona p) {
        boolean existeix=false;
        int i=0;
        while(!existeix && (i<llista.numElements())){
            if(llista.existeix(p)){
                existeix=true;
            }
            i++;
        }
        return existeix;
        }

    //Metode per a esborrar una persona de l'estructura
    public void esborrar(Persona e) throws ElementNoTrobat {
        if(existeix(e)){
            llista.esborrar(e);
        }
        else throw new ElementNoTrobat();

    }

    //Metode que ens indica en quina posicio de la llista hi ha la persona que es passa per parametre
    public int posicioPersona(Persona persona) throws ElementNoTrobat {
        if (existeix(persona)){
             return llista.buscar(persona);
        }
        else throw new ElementNoTrobat();
    }

    //Metode per a saber si la llista esta buida
    public boolean esBuida() {
       return longit==0;
    }

    //Metode per a saber el nombre d'elements de la llista
    public int numElements() {
        return longit;
    }


   //Metode per a obtenir un array amb tots els elements de la llista
    public Persona[] elements() {
        Persona[] pers= new Persona[longit];
        int i = 0;
        for(int j=0; j<llista.numElements();j++){
            try{
                pers[i]=llista.consultar(j);
                i++;
            }catch(PosicioForaRang e){}
        }
        return pers;
    }


/*
    //Metode per a obtenir una persona a partir del seu id
    public Persona buscarPerId(int id) throws ElementNoTrobat {
        int i=0;
        boolean trobat = false;
        //Iterator<V> iterador = llista.iterator();
        Persona[] pers= elements();
        while(!trobat && (i<llista.numElements())){
            if(id==pers[i].getId_persona()){
                trobat=true;
            }
            else{
                i++;
            }
        }
        if(trobat){
            return pers[i];
        }
        else throw new ElementNoTrobat();

    }


    //Metode per a obtenir un array amb totes les persones que tenen un pes inferior al valor que es passa per parametre
    public Persona[] personesPesInferior(int pes) { //iterator
        LlistaPersones llistaPer= new LlistaPersones(ordenada);

        for(int i =0; i<llista.numElements()-1; i++){
            try{
                if(llista.consultar(i).getPes()<pes){
                llistaPer.inserir(llista.consultar(i));
                }
            }catch(PosicioForaRang e){
            }
        }
        return llistaPer;
    }
     */

}

