import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Dealer } from 'app/shared/model/dealer.model';
import { DealerService } from './dealer.service';
import { DealerComponent } from './dealer.component';
import { DealerDetailComponent } from './dealer-detail.component';
import { DealerUpdateComponent } from './dealer-update.component';
import { DealerDeletePopupComponent } from './dealer-delete-dialog.component';
import { IDealer } from 'app/shared/model/dealer.model';

@Injectable({ providedIn: 'root' })
export class DealerResolve implements Resolve<IDealer> {
  constructor(private service: DealerService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDealer> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Dealer>) => response.ok),
        map((dealer: HttpResponse<Dealer>) => dealer.body)
      );
    }
    return of(new Dealer());
  }
}

export const dealerRoute: Routes = [
  {
    path: '',
    component: DealerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Dealers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DealerDetailComponent,
    resolve: {
      dealer: DealerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dealers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DealerUpdateComponent,
    resolve: {
      dealer: DealerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dealers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DealerUpdateComponent,
    resolve: {
      dealer: DealerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dealers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dealerPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DealerDeletePopupComponent,
    resolve: {
      dealer: DealerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Dealers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
