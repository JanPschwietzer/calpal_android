package com.janpschwietzer.calpal.presentation.views.settings.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.UserModel
import com.janpschwietzer.calpal.data.repository.UserRepository
import com.janpschwietzer.calpal.util.enums.ActivityLevel
import com.janpschwietzer.calpal.util.enums.DietGoal
import com.janpschwietzer.calpal.util.enums.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(
        UserModel()
    )
    val user: StateFlow<UserModel> = _user

    private val _kcalGoalEditable = MutableStateFlow(false)
    val kcalGoalEditable: StateFlow<Boolean> = _kcalGoalEditable

    fun updateName(name: String) {
        _user.value = _user.value.copy(name = name)
        updateKcalGoal()
    }

    fun updateBirthdate(birthdate: LocalDate) {
        _user.value = _user.value.copy(birthdate = birthdate)
        updateKcalGoal()
    }

    fun updateWeight(weight: Int) {
        _user.value = _user.value.copy(weight = weight)
        updateKcalGoal()
    }

    fun updateHeight(height: Int) {
        _user.value = _user.value.copy(height = height)
        updateKcalGoal()
    }

    fun updateGender(gender: Gender) {
        _user.value = _user.value.copy(gender = gender)
        updateKcalGoal()
    }

    fun updateActivityLevel(activityLevel: ActivityLevel) {
        _user.value = _user.value.copy(activityLevel = activityLevel)
        updateKcalGoal()
    }

    fun updateDietGoal(dietGoal: DietGoal) {
        _user.value = _user.value.copy(dietGoal = dietGoal)
        updateKcalGoal()
    }

    fun updateKcalGoal(kcalGoal: Int? = null) {
        if (!kcalGoalEditable.value) {
            _user.value = _user.value.copy(kcalGoal = calculateKcalGoal())
            return
        }
        _user.value = _user.value.copy(kcalGoal = kcalGoal?: calculateKcalGoal())
    }

    fun setKcalGoalEditable(editable: Boolean) {
        _kcalGoalEditable.value = editable

        if (!_kcalGoalEditable.value) {
            updateKcalGoal()
        }
    }

    private fun calculateKcalGoal(): Int {
        val metabolicRate = if (_user.value.gender == Gender.MALE) {
            (88.36 + (13.4 * _user.value.weight) + (4.8 * _user.value.height) - (5.7 * LocalDate.now().year.minus(_user.value.birthdate.year))).toInt()
        } else {
            (447.6 + (9.2 * _user.value.weight) + (3.1 * _user.value.height) - (4.3 * LocalDate.now().year.minus(_user.value.birthdate.year))).toInt()
        }

        return (metabolicRate * _user.value.activityLevel.factor * _user.value.dietGoal.factor).toInt()
    }

    fun saveUserSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveUser(_user.value)
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.getUser()
            if (user != null) {
                _user.value = user
            }
        }
    }
}