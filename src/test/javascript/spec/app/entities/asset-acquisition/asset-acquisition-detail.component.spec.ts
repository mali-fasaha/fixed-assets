/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetAcquisitionDetailComponent } from 'app/entities/asset-acquisition/asset-acquisition-detail.component';
import { AssetAcquisition } from 'app/shared/model/asset-acquisition.model';

describe('Component Tests', () => {
  describe('AssetAcquisition Management Detail Component', () => {
    let comp: AssetAcquisitionDetailComponent;
    let fixture: ComponentFixture<AssetAcquisitionDetailComponent>;
    const route = ({ data: of({ assetAcquisition: new AssetAcquisition(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetAcquisitionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssetAcquisitionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetAcquisitionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.assetAcquisition).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
