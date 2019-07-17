/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { FixedAssetInvoiceService } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice.service';
import { IFixedAssetInvoice, FixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

describe('Service Tests', () => {
  describe('FixedAssetInvoice Service', () => {
    let injector: TestBed;
    let service: FixedAssetInvoiceService;
    let httpMock: HttpTestingController;
    let elemDefault: IFixedAssetInvoice;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(FixedAssetInvoiceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new FixedAssetInvoice(0, 'AAAAAAA', currentDate, 0, false, false, 'image/png', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            invoiceDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a FixedAssetInvoice', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            invoiceDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invoiceDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new FixedAssetInvoice(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a FixedAssetInvoice', async () => {
        const returnedFromService = Object.assign(
          {
            invoiceReference: 'BBBBBB',
            invoiceDate: currentDate.format(DATE_FORMAT),
            invoiceAmount: 1,
            isProforma: true,
            isCreditNote: true,
            attachments: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            invoiceDate: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of FixedAssetInvoice', async () => {
        const returnedFromService = Object.assign(
          {
            invoiceReference: 'BBBBBB',
            invoiceDate: currentDate.format(DATE_FORMAT),
            invoiceAmount: 1,
            isProforma: true,
            isCreditNote: true,
            attachments: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            invoiceDate: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a FixedAssetInvoice', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
