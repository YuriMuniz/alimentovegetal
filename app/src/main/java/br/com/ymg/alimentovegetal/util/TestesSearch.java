package br.com.ymg.alimentovegetal.util;

/**
 * Created by yuri on 14/02/16.
 */
public class TestesSearch {

// Icon diretamente na ListFragment

/*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_default, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.grid_default_search).getActionView();
        //searchView.setOnQueryTextListener(queryListener);
    }

    private String grid_currentQuery = null; // holds the current query...

    final private SearchView.OnQueryTextListener queryListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextChange(String newText) {
            if (TextUtils.isEmpty(newText)) {
                getActivity().getActionBar().setSubtitle("List");
                grid_currentQuery = null;
            } else {
                getActivity().getActionBar().setSubtitle("List - Searching for: " + newText);
                grid_currentQuery = newText;

            }
            getLoaderManager().restartLoader(0, null, ListaVegetalFragment.this);
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
            return false;
        }
    };
    */



}
