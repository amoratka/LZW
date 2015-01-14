/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

/**
 *
 * @author Kamila
 */
public class Index {

    public int linia;
    public int ind;

    public Index(int linia, int ind) {
        this.linia = linia;
        this.ind = ind;
    }

    public int getLinia() {
        return linia;
    }

    public int getIndex() {
        return ind;
    }
}
