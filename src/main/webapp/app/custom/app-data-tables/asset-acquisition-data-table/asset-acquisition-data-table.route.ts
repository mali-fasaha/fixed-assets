import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { AssetAcquisitionUpdateComponent } from 'app/entities/asset-acquisition/asset-acquisition-update.component';
import { AssetAcquisitionDeletePopupComponent } from 'app/entities/asset-acquisition/asset-acquisition-delete-dialog.component';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { AssetAcquisitionDetailComponent } from 'app/entities/asset-acquisition/asset-acquisition-detail.component';
import { UserRouteAccessService } from 'app/core';
import { filter, map } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { AssetAcquisition, IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { Observable, of } from 'rxjs/index';
import { AssetAcquisitionDataTableService } from 'app/custom/app-data-tables/asset-acquisition-data-table/asset-acquisition-data-table.service';
import { AssetAcquisitionDataTableComponent } from 'app/custom/app-data-tables/asset-acquisition-data-table/asset-acquisition-data-table.component';

@Injectable({ providedIn: 'root' })
export class AssetAcquisitionDataTableResolve implements Resolve<IAssetAcquisition> {
  // TODO Implement and apply AssetAcquisitionDataTableService
  constructor(private service: AssetAcquisitionDataTableService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAssetAcquisition> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AssetAcquisition>) => response.ok),
        map((assetAcquisition: HttpResponse<AssetAcquisition>) => assetAcquisition.body)
      );
    }
    return of(new AssetAcquisition());
  }
}

// TODO Review routes
export const assetAcquisitionDataTableRoute: Routes = [
  {
    path: 'asset-acquisition',
    component: AssetAcquisitionDataTableComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Asset Acquisition Data'
    },
    canActivate: [UserRouteAccessService]
  }
];

// TODO Replace with date query popup
export const assetAcquisitionDataTablePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AssetAcquisitionDeletePopupComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  // TODO Implement pop up components for these paths
  {
    path: ':id/view',
    component: AssetAcquisitionDetailComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AssetAcquisitionUpdateComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AssetAcquisitionUpdateComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionDataTableResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService]
  }
];
