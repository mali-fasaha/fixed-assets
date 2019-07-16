import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { of, throwError } from 'rxjs';

import { FixedAssetsTestModule } from '../../../test.module';
import { GhaMetricsMonitoringComponent } from 'app/admin/metrics/metrics.component';
import { GhaMetricsService } from 'app/admin/metrics/metrics.service';

describe('Component Tests', () => {
  describe('GhaMetricsMonitoringComponent', () => {
    let comp: GhaMetricsMonitoringComponent;
    let fixture: ComponentFixture<GhaMetricsMonitoringComponent>;
    let service: GhaMetricsService;

    beforeEach(async(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [GhaMetricsMonitoringComponent]
      })
        .overrideTemplate(GhaMetricsMonitoringComponent, '')
        .compileComponents();
    }));

    beforeEach(() => {
      fixture = TestBed.createComponent(GhaMetricsMonitoringComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GhaMetricsService);
    });

    describe('refresh', () => {
      it('should call refresh on init', () => {
        // GIVEN
        const response = {
          timers: {
            service: 'test',
            unrelatedKey: 'test'
          },
          gauges: {
            'jcache.statistics': {
              value: 2
            },
            unrelatedKey: 'test'
          }
        };
        spyOn(service, 'getMetrics').and.returnValue(of(response));

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(service.getMetrics).toHaveBeenCalled();
      });
    });
  });
});
