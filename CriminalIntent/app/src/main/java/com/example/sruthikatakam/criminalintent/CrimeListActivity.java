package com.example.sruthikatakam.criminalintent;

/**
 * Created by sruthikatakam on 1/28/18.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected CrimeListFragment createFragment() {
        return new CrimeListFragment();
    }
}
