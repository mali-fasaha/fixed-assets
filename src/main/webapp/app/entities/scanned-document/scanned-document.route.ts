import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ScannedDocument } from 'app/shared/model/scanned-document.model';
import { ScannedDocumentService } from './scanned-document.service';
import { ScannedDocumentComponent } from './scanned-document.component';
import { ScannedDocumentDetailComponent } from './scanned-document-detail.component';
import { ScannedDocumentUpdateComponent } from './scanned-document-update.component';
import { ScannedDocumentDeletePopupComponent } from './scanned-document-delete-dialog.component';
import { IScannedDocument } from 'app/shared/model/scanned-document.model';

@Injectable({ providedIn: 'root' })
export class ScannedDocumentResolve implements Resolve<IScannedDocument> {
  constructor(private service: ScannedDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IScannedDocument> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ScannedDocument>) => response.ok),
        map((scannedDocument: HttpResponse<ScannedDocument>) => scannedDocument.body)
      );
    }
    return of(new ScannedDocument());
  }
}

export const scannedDocumentRoute: Routes = [
  {
    path: '',
    component: ScannedDocumentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ScannedDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScannedDocumentDetailComponent,
    resolve: {
      scannedDocument: ScannedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ScannedDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScannedDocumentUpdateComponent,
    resolve: {
      scannedDocument: ScannedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ScannedDocuments'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScannedDocumentUpdateComponent,
    resolve: {
      scannedDocument: ScannedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ScannedDocuments'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const scannedDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ScannedDocumentDeletePopupComponent,
    resolve: {
      scannedDocument: ScannedDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ScannedDocuments'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
