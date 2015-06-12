package lib;

import android.os.AsyncTask;

/**
 * Created by orcusz on 02/02/15.
 */
public class InternShipAsyncCall  extends AsyncTask<String, Void, InternShip> {
    private int ref;

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    @Override
    protected lib.InternShip doInBackground(String... params) {


        json.InternShip internShip = new json.InternShip();

        internShip.getInternShip(getRef());

        return internShip.getIntership();
    }
}
