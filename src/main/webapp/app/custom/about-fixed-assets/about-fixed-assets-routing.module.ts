import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ABOUT_ROUTE} from 'app/custom/about-fixed-assets/about.route';

const routes: Routes = [ABOUT_ROUTE];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AboutFixedAssetsRoutingModule { }
