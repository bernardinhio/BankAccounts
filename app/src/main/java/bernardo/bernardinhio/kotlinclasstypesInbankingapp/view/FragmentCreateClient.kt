package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class FragmentCreateClient : Fragment(){

    lateinit var etFirstName : EditText
    lateinit var etLastName : EditText
    lateinit var etDateOfBirth : EditText
    lateinit var etNationality : EditText
    lateinit var etAddress : EditText
    lateinit var etOccupation : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val viewInflated : View = inflater.inflate(R.layout.fragment_create_client, container, false)
        initializeViews(viewInflated)
        return viewInflated
    }

    private fun initializeViews(viewInflated : View) {
        etFirstName = viewInflated.findViewById(R.id.et_first_name)
        etLastName = viewInflated.findViewById(R.id.et_last_name)
        etDateOfBirth = viewInflated.findViewById(R.id.et_date_of_birth)
        etNationality = viewInflated.findViewById(R.id.et_nationality)
        etAddress = viewInflated.findViewById(R.id.et_address)
        etOccupation = viewInflated.findViewById(R.id.et_occupation)
    }
}