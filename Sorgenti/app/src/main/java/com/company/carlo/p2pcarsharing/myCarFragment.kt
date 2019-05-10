package com.company.carlo.p2pcarsharing

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.company.carlo.assets.Car
import kotlinx.android.synthetic.main.fragment_my_car.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [myCarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [myCarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class myCarFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    object CarFactory {
        val cars = ArrayList<Car>()
        fun createCar(brand: String,
                      model: String,
                      plates: String,
                      position: String,
                      registrationDate : String,
                      username: String = "polo",
                      sharingPeriodStart: String = "01 - 01 - 2019",
                      sharingPeriodEnd: String = "01 - 12 - 2019",
                      using: Boolean = false,
                      carID: Int = 777) {
            val brumbrum = Car(brand,model,plates,position,registrationDate,username,sharingPeriodStart,sharingPeriodEnd,using,carID)
            cars.add(brumbrum)
        }
        fun returnCar(index: Int): Car {
            return cars[index]
        }
        fun returnCarInfo(index : Int) : String {
            val brum = cars[index]
            return brum.brand + " " + brum.model + " " + brum.plates + "\n" + brum.registrationDate
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // CarFactory.createCar()

        CarFactory.createCar("Tesla", "Model X", "AT455BT", "Padova", "09 - 05 - 2019")
        CarFactory.createCar("BMW", "Serie 1", "AB456IY", "Padova", "09 - 05 - 2019")
        CarFactory.createCar("AUDI", "A3", "BY444MU", "Padova", "09 - 05 - 2019")
        /*
        println("cars: size "+CarFactory.cars.size)
        val brumbrum: Car = CarFactory.returnCar(0)
        println("cars: "+ brumbrum.brand + " " + brumbrum.model + " " + brumbrum.plates)
        println(CarFactory.returnCarInfo(2))
    */
    }
    private var taps = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_car, container, false)
        val rootView = inflater.inflate(R.layout.fragment_my_car,container,false)

        val btn1 = rootView.findViewById(R.id.addautobtn) as Button
        btn1.setOnClickListener {
            val intent = Intent(activity, InsertAuto::class.java)
            val message = "DC"
            intent.putExtra(AlarmClock.EXTRA_MESSAGE, message)
            startActivity(intent)
        }
        val btn2 = rootView.findViewById(R.id.provainsert) as Button //creo dinamicamento textview
        btn2.setOnClickListener {
            if(taps < CarFactory.cars.size ) {
                val tv_dynamic = TextView(this.context)
                tv_dynamic.textSize = 20f
                tv_dynamic.setPadding(5,5,5,5)
                tv_dynamic.setBackgroundResource(R.color.colorPrimary)
                tv_dynamic.text = CarFactory.returnCarInfo(taps)
                taps = taps + 1
                // add TextView to LinearLayout
                mycarlayout.addView(tv_dynamic)
            }
        }
        return rootView
    }



    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment myCarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            myCarFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
