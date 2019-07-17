import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CustomRoutingModule } from './custom-routing.module';
import { AboutFixedAssetsModule } from 'app/custom/about-fixed-assets';
import {AboutFixedAssetsRoutingModule} from 'app/custom/about-fixed-assets/about-fixed-assets-routing.module';
import { NavigationModule } from './navigation/navigation.module';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    CustomRoutingModule,
    AboutFixedAssetsModule,
    NavigationModule
  ],
  exports: [
    AboutFixedAssetsModule, AboutFixedAssetsRoutingModule
  ]
})
export class CustomModule { }
