import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AssetAcquisition } from 'app/shared/model/asset-acquisition.model';
import { AssetAcquisitionService } from './asset-acquisition.service';
import { AssetAcquisitionComponent } from './asset-acquisition.component';
import { AssetAcquisitionDetailComponent } from './asset-acquisition-detail.component';
import { AssetAcquisitionUpdateComponent } from './asset-acquisition-update.component';
import { AssetAcquisitionDeletePopupComponent } from './asset-acquisition-delete-dialog.component';
import { IAssetAcquisition } from 'app/shared/model/asset-acquisition.model';

@Injectable({ providedIn: 'root' })
export class AssetAcquisitionResolve implements Resolve<IAssetAcquisition> {
  constructor(private service: AssetAcquisitionService) {}

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

export const assetAcquisitionRoute: Routes = [
  {
    path: '',
    component: AssetAcquisitionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AssetAcquisitionDetailComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionResolve
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
      assetAcquisition: AssetAcquisitionResolve
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
      assetAcquisition: AssetAcquisitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const assetAcquisitionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AssetAcquisitionDeletePopupComponent,
    resolve: {
      assetAcquisition: AssetAcquisitionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AssetAcquisitions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
