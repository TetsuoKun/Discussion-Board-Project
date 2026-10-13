package application;


/**
* The request class represents a request in the database, used to display in the UI .
* It contains the request details including the username, description, pending, id, solution 
*/
public class Request {
    private String username; // This is the creator
    private String description; // This is the description of the request
    private boolean pending; // True for pending, false for completed
    private int id;  // the id of the request
    private String solution;  // what the admin did to finish the request. 


    /** First Constructor given the following parameters. 
     * @param username 
     * @param description
     */
    public Request (String username, String description){
        this.username = username; 
        this.description = description; 
        this.pending = true; 
        this.id = 0; 
        this.solution = ""; 
    }

    /** Second Constructor given the following parameters. 
     * @param username
     * @param description
     * @param id
     */
    public Request (String username, String description, int id){
        this.username = username; 
        this.description = description; 
        this.pending = true; 
        this.id = id; 
        this.solution = ""; 
    }

    /** Three Constructor given the following parameters. 
     * @param username
     * @param description
     * @param solution
     */
    public Request (String username, String description, String solution){
        this.username = username; 
        this.description = description; 
        this.pending = true; 
        this.id = 0; 
        this.solution = solution; 
    }

    /** Four Constructor given the following parameters.  
     * @param username
     * @param description
     * @param pending
     * @param id
     * @param solution
     */
    public Request (String username, String description, boolean pending, int id, String solution){
        this.username = username; 
        this.description = description; 
        this.pending = true; 
        this.id = id; 
        this.solution = solution; 
    }

    /** Gets the instance variable username
     * @return
     */
    public String getRequestUsername(){ return username; }
    /** Gets the instance variable description
     * @return
     */
    public String getDescription(){ return description; }
    
    /** Gets the instance variable pending
     * @return
     */
    public boolean getPending(){ return pending; }
    
    /** Gets the instance variable id
     * @return
     */
    public int getID(){ return id; }
    
    /**Gets the instance variable solution
     * @return
     */
    public String getSolution(){ return solution; }
    
    /**Sets the instance variable requestUsername
     * @param username
     */
    public void setRequestUsername(String username){ this.username = username; }
    /**Sets the instance variable username
     * @param description
     */
    public void setDescription(String description){ this.description = description;}
    /**Sets the instance variable pending
     * @param pending
     */
    public void setPending(boolean pending){this.pending = pending; }
    /**Sets the instance variable id
     * @param id
     */
    public void setID(int id){ this.id = id; }
    /**Sets the instance variable solution
     * @param solution
     */
    public void setSolution( String solution){ this.solution = solution; }
    

}
