package cat.urv.deim;

import java.util.ArrayList;
import java.util.Iterator;

import cat.urv.deim.HashMapIndirecte.Node;
import cat.urv.deim.exceptions.ElementNoTrobat;
import cat.urv.deim.exceptions.PosicioForaRang;
//si ocupa més d'un 75% redimensionar

public class LlistaNoOrdenada<E extends Comparable<E>> implements ILlistaGenerica<E> {
    private Node punter; //fantasma
    private int numElem;
    //private int max;

    public class Node{
        private E dada;
        private Node seguent;

        public Node(E p){
            this.dada=p;
            this.seguent=null;
        }
    }
    public LlistaNoOrdenada(){
        punter= new Node(null);
        numElem=0;
        punter.seguent=null;
        //max=maxim;

    }

    @Override
    public void inserir(E e) {
        Node actual= new Node(e);
        actual.seguent=punter.seguent;
        punter.seguent=actual;
        numElem++;
    }


    @Override
    public void esborrar(E e) throws ElementNoTrobat {
        Node actual = punter;
        boolean trobat=false;
        if (!existeix(e)) throw new ElementNoTrobat();
        else{
            while(!trobat && actual.seguent!=null){
                if(actual.seguent.dada.compareTo(e)==0){
                    actual.seguent=actual.seguent.seguent;
                    numElem--;
                    trobat=true;
                }
                actual=actual.seguent;

            }

        }

    }

    @Override
    public E consultar(int pos) throws PosicioForaRang {
        if(pos<0 || pos>numElem){throw new PosicioForaRang();}
       int i=0;
       boolean trobat=false;
       Node actual = punter.seguent;
        if (pos<0){
            return null;
        }
        while(i<numElem && !trobat){
            if(i==pos){
                trobat=true;
            }
            else{
            actual=actual.seguent;
            i++;
            }

        }
        if(trobat){
            return actual.dada;
        }
        else{
            return null;
        }


    }

    @Override
    public int buscar(E e) throws ElementNoTrobat {
        boolean trobat=false;
        int pos=0;
        Node actual = punter.seguent;
        while(pos<numElem && !trobat){
            if(actual.dada.compareTo(e)==0){
                trobat=true;
            }
            else{
            pos++;
            actual=actual.seguent;
            }
        }
        if(!trobat){throw new ElementNoTrobat();}
        return pos;
    }

    @Override
    public boolean existeix(E e) {
        boolean trobat=false;
        int i=0;
        Node actual = punter.seguent;
        while(!trobat && i<numElem){
            if(actual.dada.compareTo(e)==0){
                trobat=true;
            }
            else{
            i++;
            actual=actual.seguent;
            }
        }
        return trobat;
    }

    @Override
    public boolean esBuida() {
        if (numElem==0) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int numElements() {
        return numElem;
    }



    @Override
    public Iterator<E> iterator() { //mètode iterator passa qualsevol estructura a llista
        ArrayList<E> TaulaIterator=new ArrayList<>();
        Node aux=punter.seguent;

        for (int i=0; i<numElem; i++){
            TaulaIterator.add(aux.dada);
            aux=aux.seguent;
        }

        return new Iterator<E>() {
            int index=0;

            public boolean hasNext() {
                return index<TaulaIterator.size();
            }

            public E next() {
                return TaulaIterator.get(index++);
            }
        };
    }
}


