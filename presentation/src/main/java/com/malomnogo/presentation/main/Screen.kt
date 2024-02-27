package com.malomnogo.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {

    fun show(container: Int, supportFragmentManager: FragmentManager)

    abstract class Abstract(protected val clazz: Class<out Fragment>) : Screen {

        protected open fun fragment(): Fragment =
            clazz.getDeclaredConstructor().newInstance()
    }

    abstract class Replace(clazz: Class<out Fragment>) : Abstract(clazz) {

        override fun show(container: Int, supportFragmentManager: FragmentManager) {
            if (supportFragmentManager.findFragmentByTag(clazz.name) == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(container, fragment(), clazz.name)
                    .commit()
            }
        }
    }

    object Empty : Screen {

        override fun show(container: Int, supportFragmentManager: FragmentManager) = Unit
    }
}