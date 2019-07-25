import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';
import { CapitalWorkInProgressService } from './capital-work-in-progress.service';
import { CapitalWorkInProgressComponent } from './capital-work-in-progress.component';
import { CapitalWorkInProgressDetailComponent } from './capital-work-in-progress-detail.component';
import { CapitalWorkInProgressUpdateComponent } from './capital-work-in-progress-update.component';
import { CapitalWorkInProgressDeletePopupComponent } from './capital-work-in-progress-delete-dialog.component';
import { ICapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';

@Injectable({ providedIn: 'root' })
export class CapitalWorkInProgressResolve implements Resolve<ICapitalWorkInProgress> {
  constructor(private service: CapitalWorkInProgressService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICapitalWorkInProgress> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CapitalWorkInProgress>) => response.ok),
        map((capitalWorkInProgress: HttpResponse<CapitalWorkInProgress>) => capitalWorkInProgress.body)
      );
    }
    return of(new CapitalWorkInProgress());
  }
}

export const capitalWorkInProgressRoute: Routes = [
  {
    path: '',
    component: CapitalWorkInProgressComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CapitalWorkInProgressDetailComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CapitalWorkInProgressUpdateComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CapitalWorkInProgressUpdateComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const capitalWorkInProgressPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CapitalWorkInProgressDeletePopupComponent,
    resolve: {
      capitalWorkInProgress: CapitalWorkInProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CapitalWorkInProgresses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
