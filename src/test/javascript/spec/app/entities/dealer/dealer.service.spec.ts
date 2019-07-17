/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { DealerService } from 'app/entities/dealer/dealer.service';
import { IDealer, Dealer, TitleTypes } from 'app/shared/model/dealer.model';

describe('Service Tests', () => {
  describe('Dealer Service', () => {
    let injector: TestBed;
    let service: DealerService;
    let httpMock: HttpTestingController;
    let elemDefault: IDealer;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(DealerService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Dealer(
        0,
        TitleTypes.DR,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Dealer', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Dealer(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Dealer', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            dealerName: 'BBBBBB',
            dealerAddress: 'BBBBBB',
            dealerPhoneNumber: 'BBBBBB',
            dealerEmail: 'BBBBBB',
            bankName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            bankBranch: 'BBBBBB',
            bankSwiftCode: 'BBBBBB',
            bankPhysicalAddress: 'BBBBBB',
            locallyDomiciled: true,
            taxAuthorityRef: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Dealer', async () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            dealerName: 'BBBBBB',
            dealerAddress: 'BBBBBB',
            dealerPhoneNumber: 'BBBBBB',
            dealerEmail: 'BBBBBB',
            bankName: 'BBBBBB',
            bankAccountNumber: 'BBBBBB',
            bankBranch: 'BBBBBB',
            bankSwiftCode: 'BBBBBB',
            bankPhysicalAddress: 'BBBBBB',
            locallyDomiciled: true,
            taxAuthorityRef: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
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

      it('should delete a Dealer', async () => {
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
