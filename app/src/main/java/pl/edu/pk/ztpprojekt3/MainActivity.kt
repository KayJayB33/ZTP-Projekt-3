package pl.edu.pk.ztpprojekt3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import pl.edu.pk.ztpprojekt3.ui.add_edit_product.AddEditProductScreen
import pl.edu.pk.ztpprojekt3.ui.products_list.ProductListScreen
import pl.edu.pk.ztpprojekt3.ui.theme.ZTPProjekt3Theme
import pl.edu.pk.ztpprojekt3.util.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZTPProjekt3Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.PRODUCTS_LIST
                ) {
                    composable(Routes.PRODUCTS_LIST) {
                        ProductListScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                    composable(
                        route = Routes.ADD_EDIT_PRODUCT + "?productId={productId}",
                        arguments = listOf(
                            navArgument(name = "productId") {
                                type = NavType.StringType
                                defaultValue = ""
                            }
                        )
                    ) {
                        AddEditProductScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}