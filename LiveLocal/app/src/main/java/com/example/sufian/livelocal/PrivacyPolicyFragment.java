package com.example.sufian.livelocal;

import android.app.Activity;
import android.graphics.Color;
import android.text.Html;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import 	android.app.Activity;



public class PrivacyPolicyFragment extends Fragment {

    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        TextView tvId = (TextView) rootView.findViewById(R.id.label);

        tvId.setText(Html.fromHtml(text));


        // Inflate the layout for this fragment
        return rootView;
    }

    String text = "<font color='#ff0000'>Applicability</font><br><br>This privacy notice applies only to the Live Local App and explains the UConn Extension practices concerning the collection, use, and disclosure of user information. User information collected by the Live Local App will only be used as outlined in the Privacy Notice. <br><br><font color='#ff0000'>Collection and Use </font> <br><br> Except as provided below, we do not collect or use the technical information discussed in this section to identify individual users.<br><br><font color='#ff0000'> Passive/Automatic Collection </font> <br><br> The Live Local App does not collect any technical or personal information unless authorized or offered by the user as described below. <br><br><font color='#ff0000'> Active/Manual/Voluntary Collection </font><br><br> The mapping features of the Live Local app do not use location information unless specifically authorized by the user. <br><br> This app uses the Google Analytics SDK for iOS to track general usage statistics and performance. No personally identifiable data is collected through Google Analytics. Data collected is used to track the number of active users, general geographic locations of users, usage of app screens and features, and the number and type of application crashes. <br><br><font color='#ff0000'> Information Usage </font><br><br> Information about expenditures on local food and gardening products that is provided to UConn Extension voluntarily by app users is used to track total spending by all participants in the CT 10% Campaign. Any and all requests for limits on this use by owners will be honored. <br><br><font color='#ff0000'>Information Used for Contact</font> <br><br>UConn Extension will only contact a user regarding the App if the user has voluntarily provided contact information to respond to and/or acknowledge comments, problems, and feedback. <br><br><font color='#ff0000'>Use of Third Party Services</font> <br><br> The Live Local App uses facebook for users to share content with their own personal Facebook account or to the Live Local facebook group. Users should refer to Facebookâ€™s privacy policy for more information. <br><br><font color='#ff0000'>Privacy Notice Changes</font> <br><br> UConn Extension may make changes to this policy in the future. Any such changes will be consistent with our commitment to user privacy and will be clearly posted in a revised privacy notice. Only data collected from the time of the revision forward will be used under the new policy. <br><br><font color='#ff0000'>Contact Us</font> <br><br> If you feel that this site is not following its stated policy and communicating with UConn Extension does not resolve the matter, or if you have general questions or concerns about privacy or data security at the University of Connecticut, please contact the Universityâ€™s Privacy officer at (860) 486-5256 or Rachel.krinsky@uconn.edu, or the Information Security Office at security@uconn.edu or (860) 486-8255. <br><br><br> Effective: June 15, 2015";




}

