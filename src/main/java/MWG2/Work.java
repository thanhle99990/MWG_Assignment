/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MWG2;

/**
 *
 * @author Admin
 */
public class Work {
    private int bigshift;
    private int smallshift;
    private int nameshift;
    private int headcount;
    private int smallshifttime;
    private int bigshifttime;
    private String name;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    private int numworker;
    private int timecomplete;

    
    public Work(){
        
    }

    public int getBigshift() {
        return bigshift;
    }

    public int getSmallshift() {
        return smallshift;
    }

    public int getNameshift() {
        return nameshift;
    }

    public int getHeadcount() {
        return headcount;
    }

    public int getSmallshifttime() {
        return smallshifttime;
    }

    public int getBigshifttime() {
        return bigshifttime;
    }

    public String getName() {
        return name;
    }

    public int getNumworker() {
        return numworker;
    }

    public int getTimecomplete() {
        return timecomplete;
    }

    public void setBigshift(int bigshift) {
        this.bigshift = bigshift;
    }

    public void setSmallshift(int smallshift) {
        this.smallshift = smallshift;
    }

    public void setNameshift(int nameshift) {
        this.nameshift = nameshift;
    }

    public void setHeadcount(int headcount) {
        this.headcount = headcount;
    }

    public void setSmallshifttime(int smallshifttime) {
        this.smallshifttime = smallshifttime;
    }

    public void setBigshifttime(int bigshifttime) {
        this.bigshifttime = bigshifttime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumworker(int numworker) {
        this.numworker = numworker;
    }

    public void setTimecomplete(int timecomplete) {
        this.timecomplete = timecomplete;
    }
    
    
}
