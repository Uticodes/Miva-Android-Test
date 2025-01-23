package com.uticodes.mivaandroidtest.usecases

import android.content.res.Resources
import com.uticodes.mivaandroidtest.R
import com.uticodes.mivaandroidtest.data.models.Subject
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val resources: Resources
) {
    fun getSubjects(): List<Subject> = listOf(
        Subject(
            title = resources.getString(R.string.mathematics),
            icon = R.drawable.ic_math
        ),
        Subject(
            title = resources.getString(R.string.english),
            icon = R.drawable.ic_english
        ),
        Subject(
            title = resources.getString(R.string.biology),
            icon = R.drawable.ic_biology
        ),
        Subject(
            title = resources.getString(R.string.chemistry),
            icon = R.drawable.ic_chemistry
        ),
        Subject(
            title = resources.getString(R.string.physics),
            icon = R.drawable.ic_physics
        ),
        Subject(
            title = resources.getString(R.string.government),
            icon = R.drawable.ic_government
        ),
        Subject(
            title = resources.getString(R.string.accounting),
            icon = R.drawable.ic_accounting
        ),
        Subject(
            title = resources.getString(R.string.economics),
            icon = R.drawable.ic_economics
        ),
        Subject(
            title = resources.getString(R.string.literature),
            icon = R.drawable.ic_literature
        )
    )
}
