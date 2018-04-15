package com.example.sruthikatakam.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sruthikatakam.criminalintent.database.CrimeBaseHelper;
import com.example.sruthikatakam.criminalintent.database.CrimeCursorWrapper;
import com.example.sruthikatakam.criminalintent.database.CrimeDbSchema;
import com.example.sruthikatakam.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.sruthikatakam.criminalintent.database.CrimeDbSchema.CrimeTable.*;

/**
 * Created by sruthikatakam on 1/28/18.
 */

public class CrimeLab {

    private static CrimeLab sCrimeLab;

   // private List<Crime> mCrimes;
    private Context mContext;
    private  SQLiteDatabase mDatabase;
    private ArrayList mCrimes;


    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);

        }
        return sCrimeLab;
    }
    private CrimeLab(Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext)
                .getWritableDatabase();
       // mCrimes = new ArrayList<>();

       // for (int i = 0; i < 100; i++) {
         //   Crime crime = new Crime();
           // crime.setTitle("Crime #" + i);
            //crime.setSolved(i % 2 == 0); // Every other

            //mCrimes.add(crime);
       // }

    }

    public void addCrime(Crime c)
    {
        ContentValues values = getContentValues(c);
        mDatabase.insert(NAME, null, values);


       // mCrimes.add(c);
    }

    public void deleteCrime(UUID crimeId) {
        String uuidString = crimeId.toString();

        //mDatabase.delete(NAME, Cols.UUID + " = ?", new String[] {uuidString});
        mDatabase.delete(CrimeDbSchema.CrimeTable.NAME, CrimeDbSchema.CrimeTable.Cols.UUID + " = ?", new String[] {uuidString});



    }






    public List<Crime> getCrimes() {

        //return mCrimes;

        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null,
                null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }


    public Crime getCrime(UUID id) {

        //return null;
        CrimeCursorWrapper cursor = queryCrimes(
                Cols.UUID + " = ?",
                new String[] { id.toString() }
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(NAME, values,
                Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    //private Cursor queryCrimes(String whereClause,
                              // String[] whereArgs) {
    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NAME,
                null, // columns - null selects allcolumns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }







    private static ContentValues
    getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(Cols.UUID,
                crime.getId().toString());
        values.put(Cols.TITLE,
                crime.getTitle());
        values.put(Cols.DATE,
                crime.getDate().getTime());
        values.put(Cols.SOLVED,
                crime.isSolved() ? 1 : 0);
        return values;
    }






}


