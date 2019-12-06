package dev.nuris.footballleague

import android.app.Application
import dev.nuris.footballleague.http.ApiServices
import dev.nuris.footballleague.repository.*
import dev.nuris.footballleague.source.*
import dev.nuris.footballleague.ui.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainApp: Application() {
    private val appModule = module {

        single { ApiServices() }

        single<LeagueDataSource> { LeagueDataSourceImpl( get() ) }
        single<LeagueRepository> { LeagueRepositoryImpl( get() ) }
        viewModel { LeagueViewModel(get()) }

        single<LeagueDetailDataSource> { LeagueDetailDataSourceImpl( get() ) }
        single<LeagueDetailRepository> { LeagueDetailRepositoryImpl( get() ) }
        viewModel { LeagueDetailViewModel( get() ) }

        single<SearchMatchDataSource> { SearchMatchDataSourceImpl( get() ) }
        single<SearchMatchRepository> { SearchMatchRepositoryImpl( get() )}
        viewModel { SearchMatchViewModel( get() ) }

        single<EventDetailsDataSource> { EventDetailsDataSourceImpl( get() ) }
        single<EventDetailsRepository> { EventDetailsRepositoryImpl( get() )}
        viewModel { EventDetailsViewModel( get() ) }

        single<FavoriteDataSource> { FavoriteDataSourceImpl() }
        single<FavoriteRepository> { FavoriteRepositoryImpl( get() )}
        viewModel { FavoriteViewModel( get() ) }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApp)
            modules(appModule)
        }
    }
}