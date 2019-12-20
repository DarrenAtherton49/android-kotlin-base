package com.atherton.sample.presentation.features.settings.licenses

import android.content.Context
import com.atherton.sample.R

private const val APACHE_2_0_URL = "https://www.apache.org/licenses/LICENSE-2.0"
private const val GLIDE_LICENSE_URL = "https://github.com/bumptech/glide/blob/master/LICENSE"

internal fun generateLicenses(context: Context): List<License> {
    with(context) {
        val appCompat = License(
            id = 1,
            name = getString(R.string.license_app_compat_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_app_compat_description),
            url = APACHE_2_0_URL
        )
        val constraintLayout = License(
            id = 2,
            name = getString(R.string.license_constraint_layout_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_constraint_layout_description),
            url = APACHE_2_0_URL
        )
        val dagger2 = License(
            id = 3,
            name = getString(R.string.license_dagger_2_name),
            contributor = getString(R.string.license_contributor_dagger_2_authors),
            description = getString(R.string.license_dagger_2_description),
            url = APACHE_2_0_URL
        )
        val dagger2Compiler = License(
            id = 4,
            name = getString(R.string.license_dagger_2_compiler_name),
            contributor = getString(R.string.license_contributor_dagger_2_authors),
            description = getString(R.string.license_dagger_2_compiler_description),
            url = APACHE_2_0_URL
        )
        val glide = License(
            id = 5,
            name = getString(R.string.license_glide_name),
            contributor = getString(R.string.license_contributor_google_inc),
            description = getString(R.string.license_glide_description),
            url = GLIDE_LICENSE_URL
        )
        val glideCompiler = License(
            id = 6,
            name = getString(R.string.license_glide_compiler_name),
            contributor = getString(R.string.license_contributor_google_inc),
            description = getString(R.string.license_glide_compiler_description),
            url = GLIDE_LICENSE_URL
        )
        val kotlin = License(
            id = 7,
            name = getString(R.string.license_kotlin_name),
            contributor = getString(R.string.license_contributor_jetbrains),
            description = getString(R.string.license_kotlin_description),
            url = APACHE_2_0_URL
        )
        val kotlinReflect = License(
            id = 8,
            name = getString(R.string.license_kotlin_reflect_name),
            contributor = getString(R.string.license_contributor_jetbrains),
            description = getString(R.string.license_kotlin_reflect_description),
            url = APACHE_2_0_URL
        )
        val materialComponents = License(
            id = 9,
            name = getString(R.string.license_material_components_name),
            contributor = getString(R.string.license_contributor_google_inc),
            description = getString(R.string.license_material_components_description),
            url = APACHE_2_0_URL
        )
        val leakCanary = License(
            id = 10,
            name = getString(R.string.license_leak_canary_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_leak_canary_description),
            url = APACHE_2_0_URL
        )
        val leakCanaryFragment = License(
            id = 11,
            name = getString(R.string.license_leak_canary_fragment_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_leak_canary_fragment_description),
            url = APACHE_2_0_URL
        )
        val leakCanaryNoOp = License(
            id = 12,
            name = getString(R.string.license_leak_canary_no_op_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_leak_canary_no_op_description),
            url = APACHE_2_0_URL
        )
        val lifecycleCompiler = License(
            id = 13,
            name = getString(R.string.license_lifecycle_compiler_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_lifecycle_compiler_description),
            url = APACHE_2_0_URL
        )
        val lifecycleExtensions = License(
            id = 14,
            name = getString(R.string.license_lifecycle_extensions_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_lifecycle_extensions_description),
            url = APACHE_2_0_URL
        )
        val moshi = License(
            id = 15,
            name = getString(R.string.license_moshi_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_moshi_description),
            url = APACHE_2_0_URL
        )
        val moshiKotlin = License(
            id = 16,
            name = getString(R.string.license_moshi_kotlin_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_moshi_kotlin_description),
            url = APACHE_2_0_URL
        )
        val navigationFragment = License(
            id = 17,
            name = getString(R.string.license_navigation_fragment_ktx_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_navigation_fragment_ktx_description),
            url = APACHE_2_0_URL
        )
        val navigationUI = License(
            id = 18,
            name = getString(R.string.license_navigation_ui_ktx_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_navigation_ui_ktx_description),
            url = APACHE_2_0_URL
        )
        val okHttpLoggingInterceptor = License(
            id = 19,
            name = getString(R.string.license_ok_http_logging_interceptor_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_ok_http_logging_interceptor_description),
            url = APACHE_2_0_URL
        )
        val recyclerView = License(
            id = 20,
            name = getString(R.string.license_recycler_view_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_recycler_view_description),
            url = APACHE_2_0_URL
        )
        val retrofit = License(
            id = 21,
            name = getString(R.string.license_retrofit_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_retrofit_description),
            url = APACHE_2_0_URL
        )
        val retrofitMoshi = License(
            id = 22,
            name = getString(R.string.license_retrofit_moshi_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_retrofit_moshi_description),
            url = APACHE_2_0_URL
        )
        val retrofitRxJava2Adapter = License(
            id = 23,
            name = getString(R.string.license_retrofit_rxjava_2_adapter_name),
            contributor = getString(R.string.license_contributor_square_inc),
            description = getString(R.string.license_retrofit_rxjava_2_adapter_description),
            url = APACHE_2_0_URL
        )
        val roomRuntime = License(
            id = 24,
            name = getString(R.string.license_room_runtime_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_room_runtime_description),
            url = APACHE_2_0_URL
        )
        val roomCompiler = License(
            id = 25,
            name = getString(R.string.license_room_compiler_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_room_compiler_description),
            url = APACHE_2_0_URL
        )
        val roomRxJava2 = License(
            id = 26,
            name = getString(R.string.license_room_rxjava2_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_room_rxjava2_description),
            url = APACHE_2_0_URL
        )
        val roxie = License(
            id = 27,
            name = getString(R.string.license_roxie_name),
            contributor = getString(R.string.license_contributor_ww_tech),
            description = getString(R.string.license_roxie_description),
            url = APACHE_2_0_URL
        )
        val rxAndroid = License(
            id = 28,
            name = getString(R.string.license_rx_android_name),
            contributor = getString(R.string.license_contributor_rx_android_authors),
            description = getString(R.string.license_rx_android_description),
            url = APACHE_2_0_URL
        )
        val rxJava2 = License(
            id = 29,
            name = getString(R.string.license_rxjava_2_name),
            contributor = getString(R.string.license_contributor_rxjava_2_authors),
            description = getString(R.string.license_rxjava_2_description),
            url = APACHE_2_0_URL
        )
        val rxKotlin = License(
            id = 30,
            name = getString(R.string.license_rxkotlin_name),
            contributor = getString(R.string.license_contributor_rxjava_2_authors),
            description = getString(R.string.license_rxkotlin_description),
            url = APACHE_2_0_URL
        )
        val rxRelay = License(
            id = 31,
            name = getString(R.string.license_rxrelay_name),
            contributor = getString(R.string.license_contributor_netflix_and_jake_wharton),
            description = getString(R.string.license_rxrelay_description),
            url = APACHE_2_0_URL
        )
        val timber = License(
            id = 32,
            name = getString(R.string.license_timber_name),
            contributor = getString(R.string.license_contributor_jake_wharton),
            description = getString(R.string.license_timber_description),
            url = APACHE_2_0_URL
        )
        val palette = License(
            id = 33,
            name = getString(R.string.license_palette_name),
            contributor = getString(R.string.license_contributor_android_open_source),
            description = getString(R.string.license_palette_description),
            url = APACHE_2_0_URL
        )

        return listOf(
            appCompat,
            constraintLayout,
            dagger2,
            dagger2Compiler,
            glide,
            glideCompiler,
            kotlin,
            kotlinReflect,
            materialComponents,
            leakCanary,
            leakCanaryFragment,
            leakCanaryNoOp,
            lifecycleCompiler,
            lifecycleExtensions,
            moshi,
            moshiKotlin,
            navigationFragment,
            navigationUI,
            okHttpLoggingInterceptor,
            palette,
            recyclerView,
            retrofit,
            retrofitMoshi,
            retrofitRxJava2Adapter,
            roomRuntime,
            roomCompiler,
            roomRxJava2,
            roxie,
            rxAndroid,
            rxJava2,
            rxKotlin,
            rxRelay,
            timber
        ).sortedBy { it.name }
    }
}
