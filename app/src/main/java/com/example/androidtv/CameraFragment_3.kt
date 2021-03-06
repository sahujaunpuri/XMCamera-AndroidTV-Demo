package com.example.testproject

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidtv.MainActivity
import com.example.androidtv.R
import com.lib.FunSDK
import com.lib.funsdk.support.FunDevicePassword
import com.lib.funsdk.support.FunError
import com.lib.funsdk.support.FunSupport
import com.lib.funsdk.support.OnFunDeviceOptListener
import com.lib.funsdk.support.config.ChannelSystemFunction
import com.lib.funsdk.support.config.OPPTZControl
import com.lib.funsdk.support.config.OPPTZPreset
import com.lib.funsdk.support.config.SystemInfo
import com.lib.funsdk.support.models.FunDevType
import com.lib.funsdk.support.models.FunDevice
import com.lib.funsdk.support.models.FunStreamType
import com.lib.funsdk.support.widget.FunVideoView
import com.lib.sdk.struct.H264_DVR_FILE_DATA
import kotlinx.android.synthetic.main.activity_multi_screen.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment_3.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment_3 : Fragment(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener, OnFunDeviceOptListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var mFunDevice: FunDevice? = null
    private var mFunVideoView: FunVideoView? = null
    private var mChannelCount = 0
    private var mCanToPlay = false
    var NativeLoginPsw: String? = null
    private var isGetSysFirst = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_camera_3, container, false)
        init(view)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CameraFragment_3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment_3().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun init(view: View) {
        val devId = -128965554
        if (FunSupport.getInstance().findDeviceById(devId) == null || !FunSupport.getInstance()
                .hasLogin()
        ) {
            startLogin()
        }
        mFunDevice = FunSupport.getInstance().findDeviceById(devId)
        if (null == mFunDevice) {
            return
        }

        mFunVideoView = view.findViewById<View>(R.id.funVideoView) as FunVideoView
        if (mFunDevice!!.devType == FunDevType.EE_DEV_LAMP_FISHEYE) {
            // ????????????,??????????????????
            mFunVideoView?.setFishEye(true)
        }
        mFunVideoView!!.setOnPreparedListener(this)
        mFunVideoView!!.setOnErrorListener(this)
        mFunVideoView!!.setOnInfoListener(this)

        mCanToPlay = false
        FunSupport.getInstance().registerOnFunDeviceOptListener(this)
        if (!mFunDevice!!.hasLogin() || !mFunDevice!!.hasConnected()) {
            loginDevice()
        } else {
            requestSystemInfo()
            requestChannelSystemFunction()
        }
    }

    private fun requestChannelSystemFunction() {
        val channelSystemFunction = ChannelSystemFunction()
        FunSupport.getInstance().requestDeviceCmdGeneral(mFunDevice, channelSystemFunction)
    }

    private fun requestSystemInfo() {
        FunSupport.getInstance().requestDeviceConfig(mFunDevice, SystemInfo.CONFIG_NAME)
    }

    private fun startLogin() {
        val intent = Intent()
        intent.setClass(activity!!, MainActivity::class.java)
        startActivity(intent)
    }

    private fun loginDevice() {
        FunSupport.getInstance().requestDeviceLogin(mFunDevice)
    }

    override fun onPrepared(mp: MediaPlayer?) {
//        TODO("Not yet implemented")
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {

        // ????????????
//        Toast.makeText(
//            activity,
//            resources.getString(R.string.media_play_error) + " : "
//                    + FunError.getErrorStr(extra), Toast.LENGTH_SHORT
//        ).show()

        if ((FunError.EE_TPS_NOT_SUP_MAIN == extra
                    || FunError.EE_DSS_NOT_SUP_MAIN == extra)
        ) {
            // ?????????????????????,?????????????????????????????????
            if (null != mFunVideoView) {
                mFunVideoView!!.streamType = FunStreamType.STREAM_SECONDARY
                playRealMedia(mFunVideoView, mFunDevice)
            }
        }

        return true
    }

    private fun playRealMedia(funViewVideo : FunVideoView?, funDevice: FunDevice?) {


        // ????????????: ??????????????????...
//        textVideoStat.setText(R.string.media_player_opening)
//        textVideoStat.visibility = View.VISIBLE

        if (mFunDevice!!.isRemote) {
            funViewVideo!!.setRealDevice(funDevice!!.getDevSn(), funDevice.CurrChannel)
        } else {
            val deviceIp = FunSupport.getInstance().deviceWifiManager.gatewayIp
            funViewVideo!!.setRealDevice(deviceIp, funDevice!!.CurrChannel)
        }

        // ????????????

        // ????????????
        funViewVideo.setMediaSound(true)
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            textVideoStat.setText(R.string.media_player_buffering)
            textVideoStat.visibility = View.VISIBLE
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            textVideoStat.visibility = View.GONE
        }
        return true
    }

    override fun onDeviceLoginSuccess(funDevice: FunDevice?) {
        println("TTT---->>>> loginsuccess")

        if (null != mFunDevice && null != funDevice) {
            if (mFunDevice!!.id == funDevice.id) {

                // ???????????????????????????SystemInfo
                // ?????????????????????SystemInfo,??????????????????????????????:playRealMedia();
                requestSystemInfo()
                //???????????????????????????
                requestChannelSystemFunction()
            }
        }
    }

    override fun onDeviceLoginFailed(funDevice: FunDevice?, errCode: Int?) {

        // ??????????????????
//        hideWaitDialog();
//        Toast.makeText(activity, FunError.getErrorStr(errCode), Toast.LENGTH_SHORT).show()

        // ???????????????????????????,????????????????????????,????????????????????????

        // ???????????????????????????,????????????????????????,????????????????????????
        if (errCode == FunError.EE_DVR_PASSWORD_NOT_VALID) {
            if (null != mFunDevice) {
                NativeLoginPsw = "123"
                onDeviceSaveNativePws()
                loginDevice()
            }
//            showInputPasswordDialog()
        }
    }

    private fun onDeviceSaveNativePws() {
        FunDevicePassword.getInstance().saveDevicePassword(
            mFunDevice!!.getDevSn(),
            NativeLoginPsw
        )
        // ?????????????????????????????????
        if (FunSupport.getInstance().saveNativePassword) {
            FunSDK.DevSetLocalPwd(mFunDevice!!.getDevSn(), "admin", NativeLoginPsw)
        }
    }

    override fun onDeviceGetConfigSuccess(funDevice: FunDevice?, configName: String?, nSeq: Int) {
        var channelCount = 0
        if (SystemInfo.CONFIG_NAME == configName) {
            if (!isGetSysFirst) {
                return
            }

            // ??????UI
            //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (funDevice!!.channel == null) {
                FunSupport.getInstance().requestGetDevChnName(funDevice)
                requestSystemInfo()
                requestChannelSystemFunction()
                return
            }
            channelCount = funDevice.channel.nChnCount
            // if (channelCount >= 5) {
            // channelCount = 5;
            // }
            if (channelCount > 1) {
                mChannelCount = channelCount
//                addChannelBtn(channelCount)
            }

//            hideWaitDialog();

            // ????????????????????????
            mCanToPlay = true
            isGetSysFirst = false
//            Toast.makeText(activity, "" + getType(funDevice.netConnectType), Toast.LENGTH_SHORT).show()

            // ?????????????????????,??????WiFi????????????????????????
            // ???????????????????????????
            playRealMedia(mFunVideoView, mFunDevice)
//            if (MyUtils.detectWifiNetwork(activity)) {
//                playRealMedia(mFunVideoView, mFunDevice)
//            } else {
////                Toast.makeText(
////                    activity,
////                    R.string.meida_not_auto_play_because_no_wifi,
////                    Toast.LENGTH_SHORT
////                ).show()
//            }
        } else if (OPPTZPreset.CONFIG_NAME == configName) {
        } else if (OPPTZControl.CONFIG_NAME == configName) {
//            Toast.makeText(activity, R.string.user_set_preset_succeed, Toast.LENGTH_SHORT)
//                .show()

            // ???????????????????????????
//			requestPTZPreset();
        } else if (ChannelSystemFunction.CONFIG_NAME == configName) {
        }
    }

    private fun getType(i: Int): Any {
        return when (i) {
            0 -> "P2P"
            1 -> "Forward"
            2 -> "IP"
            5 -> "RPS"
            else -> ""
        }
    }

    override fun onDeviceGetConfigFailed(funDevice: FunDevice?, errCode: Int?) {
//        Toast.makeText(activity, FunError.getErrorStr(errCode), Toast.LENGTH_SHORT).show()
        if (errCode == -11406) {
            funDevice!!.invalidConfig(OPPTZPreset.CONFIG_NAME)
        }
    }

    override fun onDeviceSetConfigSuccess(funDevice: FunDevice?, configName: String?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceSetConfigFailed(
        funDevice: FunDevice?,
        configName: String?,
        errCode: Int?
    ) {
        if (OPPTZControl.CONFIG_NAME == configName) {
//            Toast.makeText(activity, R.string.user_set_preset_fail, Toast.LENGTH_SHORT)
//                .show()
        }
    }

    override fun onDeviceChangeInfoSuccess(funDevice: FunDevice?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceChangeInfoFailed(funDevice: FunDevice?, errCode: Int?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceOptionSuccess(funDevice: FunDevice?, option: String?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceOptionFailed(funDevice: FunDevice?, option: String?, errCode: Int?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceFileListChanged(funDevice: FunDevice?) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceFileListChanged(
        funDevice: FunDevice?,
        datas: Array<out H264_DVR_FILE_DATA>?
    ) {
//        TODO("Not yet implemented")
    }

    override fun onDeviceFileListGetFailed(funDevice: FunDevice?) {
//        TODO("Not yet implemented")
    }
}