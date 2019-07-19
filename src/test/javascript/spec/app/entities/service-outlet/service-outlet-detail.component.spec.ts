/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { ServiceOutletDetailComponent } from 'app/entities/service-outlet/service-outlet-detail.component';
import { ServiceOutlet } from 'app/shared/model/service-outlet.model';

describe('Component Tests', () => {
  describe('ServiceOutlet Management Detail Component', () => {
    let comp: ServiceOutletDetailComponent;
    let fixture: ComponentFixture<ServiceOutletDetailComponent>;
    const route = ({ data: of({ serviceOutlet: new ServiceOutlet(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [ServiceOutletDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ServiceOutletDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOutletDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceOutlet).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
