import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { errorRoute, navbarRoute } from './layouts';
import { DEBUG_INFO_ENABLED } from 'app/app.constants';

const LAYOUT_ROUTES = [navbarRoute, ...errorRoute];

@NgModule({
  imports: [
    RouterModule.forRoot(
      [
        {
          path: 'admin',
          loadChildren: './admin/admin.module#FixedAssetsAdminModule'
        },
        {
          path: 'assets',
          loadChildren: './custom/about-fixed-assets/about-fixed-assets.module#AboutFixedAssetsModule'
        },
        {
          path: 'data',
          loadChildren: './custom/app-data-tables/app-data-tables.module#AppDataTablesModule'
        },
        {
          path: '',
          loadChildren: './custom/gha-dashboard/gha-dashboard.module#GhaDashboardModule'
        },
        ...LAYOUT_ROUTES
      ],
      { enableTracing: DEBUG_INFO_ENABLED }
    )
  ],
  exports: [RouterModule]
})
export class FixedAssetsAppRoutingModule {}
