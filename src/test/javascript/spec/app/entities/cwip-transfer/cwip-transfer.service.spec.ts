/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CwipTransferService } from 'app/entities/cwip-transfer/cwip-transfer.service';
import { ICwipTransfer, CwipTransfer } from 'app/shared/model/cwip-transfer.model';

describe('Service Tests', () => {
  describe('CwipTransfer Service', () => {
    let injector: TestBed;
    let service: CwipTransferService;
    let httpMock: HttpTestingController;
    let elemDefault: ICwipTransfer;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CwipTransferService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CwipTransfer(0, currentDate, 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            transferMonth: currentDate.format(DATE_FORMAT)
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

      it('should create a CwipTransfer', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            transferMonth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transferMonth: currentDate
          },
          returnedFromService
        );
        service
          .create(new CwipTransfer(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CwipTransfer', async () => {
        const returnedFromService = Object.assign(
          {
            transferMonth: currentDate.format(DATE_FORMAT),
            assetSerialTag: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            transferTransactionId: 1,
            assetCategoryId: 1,
            cwipTransactionId: 1,
            transferDetails: 'BBBBBB',
            transferAmount: 1,
            dealerId: 1,
            transactionInvoiceId: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            transferMonth: currentDate
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

      it('should return a list of CwipTransfer', async () => {
        const returnedFromService = Object.assign(
          {
            transferMonth: currentDate.format(DATE_FORMAT),
            assetSerialTag: 'BBBBBB',
            serviceOutletCode: 'BBBBBB',
            transferTransactionId: 1,
            assetCategoryId: 1,
            cwipTransactionId: 1,
            transferDetails: 'BBBBBB',
            transferAmount: 1,
            dealerId: 1,
            transactionInvoiceId: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            transferMonth: currentDate
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

      it('should delete a CwipTransfer', async () => {
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
