package edu.lehigh.cse216.group4.backend;

import java.util.ArrayList;

/**
 * DataStore provides access to a set of objects, and makes sure that each has
 * a unique identifier that remains unique even after the object is deleted.
 * 
 * We follow the convention that member fields of a class have names that start
 * with a lowercase 'm' character, and are in camelCase.
 * 
 * NB: The methods of DataStore are synchronized, since they will be used from a 
 * web framework and there may be multiple concurrent accesses to the DataStore.
 */
public class DataStore {

    private Database db;

    /**
     * Construct the DataStore by resetting its counter and creating the
     * ArrayList for the rows of data.
     */
    DataStore() {

    }

    /**
     * Add a new row to the DataStore
     * 
     * Note: we return -1 on an error.  There are many good ways to handle an 
     * error, to include throwing an exception.  In robust code, returning -1 
     * may not be the most appropriate technique, but it is sufficient for this 
     * tutorial.
     * 
     * @param title The title for this newly added row
     * @param content The content for this row
     * @return the ID of the new row, or -1 if no row was created
     */
    public synchronized int createEntry(String title, String content) {
        if (title == null || content == null)
            return -1;
        // NB: we can safely assume that id is greater than the largest index in 
        //     mRows, and thus we can use the index-based add() method
        //int id = mCounter++;
        //DataRow data = new DataRow(id, title, content);
        int ret = db.insertRow(title, content);
        //mRows.add(id, data);
        return ret;
    }
    public synchronized int attachDB(Database db){
        if(db == null)
            return -1;
        this.db = db;
        return 0;
    }
    /**
     * Get one complete row from the DataStore using its ID to select it
     * 
     * @param id The id of the row to select
     * @return A copy of the data in the row, if it exists, or null otherwise
     */
    public synchronized Database.RowData readOne(int id) {
       // if (id >= mRows.size())
       //     return null;
        Database.RowData data = db.selectOne(id);
        if (data == null)
            return null;
        return new Database.RowData(data);
    }

    /**
     * Get all of the ids and titles that are present in the DataStore
     * @return An ArrayList with all of the data
     */
    public synchronized ArrayList<Database.RowDataLite> readAll() {
        ArrayList<Database.RowData> allRows = db.selectAll();
        ArrayList<Database.RowDataLite> data = new ArrayList<Database.RowDataLite>();
        // NB: we copy the data, so that our ArrayList only has ids and titles
        for (Database.RowData row : allRows) {
            if (row != null)
                data.add(new Database.RowDataLite(row));
        }
        return data;
    }
    /**
     * Update the title and content of a row in the DataStore
     *
     * @param id The Id of the row to update
     * @param title The new title for the row
     * @param content The new content for the row
     * @return a copy of the data in the row, if it exists, or null otherwise
     */
    public synchronized Database.RowData updateOne(int id, String title, String content) {
        // Do not update if we don't have valid data
        if (title == null || content == null)
            return null;
        // Only update if the current entry is valid (not null)
        //if (id >= mRows.size())
        //    return null;
        Database.RowData data = db.selectOne(id);
        if (data == null)
            return null;
        // Update and then return a copy of the data, as a DataRow
        data.mSubject = title;
        data.mMessage = content;
        int res = db.updateOne(id, content);
        if(res == -1){return null;}
        return new Database.RowData(data);
    }

    /**
     * Delete a row from the DataStore
     * 
     * @param id The Id of the row to delete
     * @return true if the row was deleted, false otherwise
     */
    public synchronized boolean deleteOne(int id) {
        int res = db.deleteRow(id);
        if(res == -1){return false;}
        return true;
    }
    
}
