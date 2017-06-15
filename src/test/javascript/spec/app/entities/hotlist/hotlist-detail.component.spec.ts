import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ApiTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HotlistDetailComponent } from '../../../../../../main/webapp/app/entities/hotlist/hotlist-detail.component';
import { HotlistService } from '../../../../../../main/webapp/app/entities/hotlist/hotlist.service';
import { Hotlist } from '../../../../../../main/webapp/app/entities/hotlist/hotlist.model';

describe('Component Tests', () => {

    describe('Hotlist Management Detail Component', () => {
        let comp: HotlistDetailComponent;
        let fixture: ComponentFixture<HotlistDetailComponent>;
        let service: HotlistService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApiTestTestModule],
                declarations: [HotlistDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HotlistService,
                    EventManager
                ]
            }).overrideTemplate(HotlistDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HotlistDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HotlistService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Hotlist(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hotlist).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
