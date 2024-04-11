package cat.urv.deim;
import java.util.ArrayList;
import cat.urv.deim.exceptions.ElementNoTrobat;
import java.util.Iterator;

public class HashMapIndirecte<K, V> implements IHashMap<K, V>{
    private ArrayList<Node> taulaHashing;
    private int nElem;
    private int mida;

    public class Node{
        private K clau;
        private V valor;
        private Node seguent;

        public Node(K clau, V val){
            this.clau=clau;
            this.valor=val;
            this.seguent=null;
        }
    }
    //constructor
    public HashMapIndirecte(int mida){
        //ArrayList <Node> taulaHashing; //entre <> tipus de dades q utilitzo
        taulaHashing= new ArrayList<Node>(mida);
        nElem=0;
        this.mida=mida;
        //x guardar elements -> part estatica de la taula amb una estructura del tipus ArrayList (java.util.ArrayList).
        for(int i =0; i <mida; i++){
            taulaHashing.add(i, null);
        }
    }

    // Metode per insertar un element a la taula. Si existeix un element amb aquesta clau s'actualitza el valor
    public void inserir(K key, V value){
        Node nou= new Node(key, value);
        int pos =(int)key%midaTaula();//key.hashcode()???????
        boolean trobat =false;
        if(taulaHashing.get(pos)==null){
            taulaHashing.set(pos, nou);
            nElem++;
        }else{
            Node aux=taulaHashing.get(pos) ;
            while (aux!=null && !trobat) {
                if(key.equals(aux.clau)){
                    taulaHashing.get(pos).valor =value;
                    trobat=true;
                }else{
                    aux=aux.seguent;
                }
            }
            if(!trobat){
                taulaHashing.set(pos, aux);
                nElem++;
            }
        }

        //COMPAREM SI HI HA MES DEL 75% OCUPAT
        if (factorCarrega()>0.75){ // fer taula nova el doble de gran -> esborrar vella i fer recorregut x inserir elements(actualitz. nodes)
            //creem taula nova
            int midaNova = midaTaula()*2;
            ArrayList<Node> nova = new ArrayList<>(midaNova);
            for(int i =0; i <midaNova; i++){
                nova.add(i, null);
            }
            //int posNou =(int)key%midaTaula();
            Node aux=null;
            //recorregut
            for (Node node : taulaHashing) { //tipus de variable / nom variable en la q opera el bucle / nom llista d'on treiem components

                if (node!= null) {
                    int posNou = ((int)node.clau%midaNova);
                    if (nova.get(posNou) == null) {
                        nova.set(posNou, node);
                    } else {
                        aux = nova.get(posNou);
                        node.seguent=aux.seguent;
                        aux.seguent = node;

                    }
                }
            }
            taulaHashing = nova;
            mida = midaNova;
        }
    }


    // Metode per a obtenir un array amb tots els elements de K
    public V consultar(K key) throws ElementNoTrobat{
        int hashCode =(int)key.hashCode()%midaTaula();
        Node aux= taulaHashing.get(hashCode);
        //Iterator<V> t= this.iterator(); //taula del HahsMap
        while(aux!=null){
            if (aux.clau.equals(key)) {
                return aux.valor;
            }
            aux= aux.seguent;
        }
        throw new ElementNoTrobat();
    }

    // Metode per a esborrar un element de la taula de hash
    public void esborrar(K key) throws ElementNoTrobat{
        int hashCode = (int)key%midaTaula();
        Node actual = taulaHashing.get(hashCode);
        Node ant = null;
        boolean borrat=false;
        while(actual!=null&& !borrat){
            if(actual.clau.equals(key)){
                if(ant==null){
                    taulaHashing.set(hashCode, actual.seguent);
                    borrat=true;
                } else {
                    ant.seguent = actual.seguent;
                }
                nElem--;
            }
            ant = actual;
            actual = actual.seguent;
        }
        if (!borrat){
            throw new ElementNoTrobat();
        }
    }

    // Metode per a comprovar si un element esta a la taula de hash
    public boolean buscar(K key){
        int hashCode = (int)key.hashCode()%midaTaula();
        Node actual = taulaHashing.get(hashCode);
        while(actual != null) {
            if (actual.clau.equals(key)) {
                return true;
            }
            actual=actual.seguent;
        }
        return false;
    }

    // Metode per a comprovar si la taula te elements
    public boolean esBuida(){
        return nElem==0;
    }

    // Metode per a obtenir el nombre d'elements de la llista
    public int numElements(){
        return nElem;
    }

    // Metode per a obtenir les claus de la taula
    public K[] obtenirClaus(){
       ArrayList<K> claus = new ArrayList<>(nElem);
        //int k = 0;
        for (int i = 0; i < midaTaula(); i++) {
            Node actual = taulaHashing.get(i);
            while (actual!=null) {
                claus.add(actual.clau);
                actual=actual.seguent;
            }
        }
        return (K[]) claus.toArray();
    }

    // Metode per a saber el factor de carrega actual de la taula
    public float factorCarrega(){
        return (float) nElem/mida;
    }

    // Metode per a saber la mida actual de la taula (la mida de la part estatica)
    public int midaTaula(){
        return this.mida;
    }

    // Metode per a poder iterar pels elements de la taula
    // IMPORTANT: El recorregut s'ha de fer de forma ORDENADA SEGONS LA CLAU
    //iterar sobre la informacio que hi ha guardada **de forma ordenada**, retornant un **objecte Iterable** que ens permeti recorrer tota la informacio.
    public Iterator<V> iterator() {
        ArrayList<V> TaulaIterator=new ArrayList<>();
        Node aux;

        for (int i=0; i<taulaHashing.size(); i++){
            aux=taulaHashing.get(i);
            while (aux!=null){
                TaulaIterator.add(aux.valor);
                aux=aux.seguent;
            }
        }

        return new Iterator<V>() {
            int index=0;

            public boolean hasNext() {
                return index<TaulaIterator.size();
            }

            public V next() {
                return TaulaIterator.get(index++);
            }
        };

    }

    }



