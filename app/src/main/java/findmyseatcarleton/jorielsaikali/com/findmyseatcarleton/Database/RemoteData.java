package findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.util.concurrent.ExecutionException;

import findmyseatcarleton.jorielsaikali.com.findmyseatcarleton.View.MainActivity;

public class RemoteData extends AsyncTask<String, Void, String> {

    private final String TAG = "RemoteData";

    private String link = "https://findmyseatcarleton.jorielsaikali.com/index.php";
    private MutableLiveData<String> result;

    public RemoteData() {

    }

    public LiveData<String> getResult(String[] args) {
        for (int i = 0; i < args.length; i++) {
            Log.i(TAG, "args: " + args[i]);
        }

        if (result == null) {
            result = new MutableLiveData<>();
        }

        try {
            result.setValue(this.execute(args).get());
            //Log.i(TAG, "result: " + result.getValue());
            return result;
        } catch (InterruptedException ie) {
            return null;
        } catch (ExecutionException ee) {
            return null;
        }
    }

    @Override
    protected String doInBackground(String[] args) {
        String functionality = args[0]; // getting which functionality to run

        switch(functionality) {
            case "REGISTER":
                return register(args);
            case "LOGIN":
                return login(args);
            case "FIND":
                return find(args);
            case "UPDATE":
                return update(args);
            case "ADD ENTRY":
                return addEntry();
            case "RESET ENTRY":
                return resetEntry();
            case "SELECT WINNER":
                return selectWinnerAndEmail();
            case "BUILDING LIST":
                return buildingList();
            case "FLOOR LIST":
                return floorList(args);
            case "GET SALT":
                return getSalt(args);
            case "GET PROFILE DATA":
                return getProfileData(args);
            case "CONFIRM OLD PASSWORD":
                return confirmPassword(args);
            case "UPDATE PASSWORD":
                return updatePassword(args);
            case "UPDATE EMAIL":
                return updateEmail(args);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String res) {

    }

    private String serverResponseGet(String data) {
        try {
            URL obj = new URL(link + "?" + data);
            Log.i(TAG, obj.toString());
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");

            int responseCode = con.getResponseCode();
            Log.i(TAG, String.valueOf(responseCode));
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                return response.toString();
            }

            return "RemoteData: Failed Get Request";

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String serverResponsePost(String data) {
        try {
            // ---------- Opening connection to link ---------- //
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            // ------------------------------------------------ //

            // ----------- Writing data to URL --------- //
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            // ----------------------------------------- //

            // ------- Setting up for reading responses from URL ------- //
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            // --------------------------------------------------------- //

            // ----------- Reading server response ----------- //
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            // ----------------------------------------------- //

            return sb.toString();
        } catch (Exception e) {
            Log.i(TAG, "Exception happened in serverResponsePost");
            return "Exception: " + e.getMessage();
        }
    }

    private String register(String[] args) {
        // for registering, we need to POST a 'register', 'username', 'email', 'salt', 'hash'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            String email = args[2];
            String salt = args[3];
            String hash = args[4];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("register", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("salt", "UTF-8") + "=" + URLEncoder.encode(salt, "UTF-8");
            data += "&" + URLEncoder.encode("hash", "UTF-8") + "=" + URLEncoder.encode(hash, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String login(String[] args) {
        // for logging in, we need to POST a 'login', 'username', 'hash'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            String hash = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("login", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("hash", "UTF-8") + "=" + URLEncoder.encode(hash, "UTF-8");
            // ------------------------------------ //

            Log.i(TAG, "data: " + data);

            return serverResponsePost(data);

        } catch (Exception e) {
            Log.i(TAG, "Exception happened in login");
            return "Exception: " + e.getMessage();
        }
    }

    private String find(String[] args) {
        // for finding, we need to POST a 'find', 'number_of_seats', 'building', 'floor'
        try {
            // ----------- Getting data passed ----------- //
            String numberOfSeats = args[1];
            String building = args[2];
            String floor = args[3];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("find", "UTF-8");
            data += "&" + URLEncoder.encode("number_of_seats", "UTF-8") + "=" + URLEncoder.encode(numberOfSeats, "UTF-8");
            data += "&" + URLEncoder.encode("building", "UTF-8") + "=" + URLEncoder.encode(building, "UTF-8");
            data += "&" + URLEncoder.encode("floor", "UTF-8") + "=" + URLEncoder.encode(floor, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String update(String[] args) {
        // for updating, we need to GET a 'update', 'tableID', 'status'
        try {
            // ----------- Getting data passed ----------- //
            String tableID = args[1];
            String status = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("update", "UTF-8");
            data += "&" + URLEncoder.encode("tableID", "UTF-8") + "=" + URLEncoder.encode(tableID, "UTF-8");
            data += "&" + URLEncoder.encode("status", "UTF-8") + "=" + URLEncoder.encode(status, "UTF-8");
            // ------------------------------------ //

            return serverResponseGet(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String addEntry() {
        // for adding an entry, we need to POST a 'addEntry', 'userID'
        try {
            // ----------- Getting data passed ----------- //
            String username = MainActivity.username;
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("addEntry", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            // ------------------------------------ //

            Log.i(TAG, "data: " + data);

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String resetEntry() {
        // for resetting all current draws entries, we need to POST a 'resetEntry'
        try {
            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("resetEntry", "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String selectWinnerAndEmail() {
        // for selecting a random winner and emailing them, we need to POST a 'emailWinner'
        try {
            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("emailWinner", "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String buildingList() {
        // for getting building lisit, we need to POST a 'buildingList'
        try {
            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("buildingList", "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String floorList(String[] args) {
        // for getting floor list, we need to POST a 'floorList', 'floorListBuilding'
        try {
            // ----------- Getting data passed ----------- //
            String floorListBuilding = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("floorList", "UTF-8");
            data += "&" + URLEncoder.encode("floorListBuilding", "UTF-8") + "=" + URLEncoder.encode(floorListBuilding, "UTF-8");
            // ------------------------------------ //

            Log.i(TAG, "data: " + data);

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String getSalt(String[] args) {
        // for getting salt, we need to POST a 'getSalt', 'username'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("getSalt", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String getProfileData(String[] args) {
        // for getting entries, we need to POST a 'profile' and 'username'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("profile", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String confirmPassword(String[] args) {
        // for confirming password, we need to POST a 'confirmPassword', 'username', 'oldPassword'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            String oldPassword = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("confirmPassword", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("oldPassword", "UTF-8") + "=" + URLEncoder.encode(oldPassword, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String updatePassword(String[] args) {
        // for updating password, we need to POST a 'updatePassword', 'username', 'newPassword'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            String newPassword = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("updatePassword", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("newPassword", "UTF-8") + "=" + URLEncoder.encode(newPassword, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private String updateEmail(String[] args) {
        // for updating email, we need to POST a 'updateEmail', 'username', 'newEmail'
        try {
            // ----------- Getting data passed ----------- //
            String username = args[1];
            String newEmail = args[2];
            // ------------------------------------------- //

            // ----------- Encoding data ---------- //
            String data = URLEncoder.encode("updateEmail", "UTF-8");
            data += "&" + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("newEmail", "UTF-8") + "=" + URLEncoder.encode(newEmail, "UTF-8");
            // ------------------------------------ //

            return serverResponsePost(data);

        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }
}
