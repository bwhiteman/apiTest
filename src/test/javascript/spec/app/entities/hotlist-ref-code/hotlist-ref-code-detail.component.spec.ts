import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ApiTestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HotlistRefCodeDetailComponent } from '../../../../../../main/webapp/app/entities/hotlist-ref-code/hotlist-ref-code-detail.component';
import { HotlistRefCodeService } from '../../../../../../main/webapp/app/entities/hotlist-ref-code/hotlist-ref-code.service';
import { HotlistRefCode } from '../../../../../../main/webapp/app/entities/hotlist-ref-code/hotlist-ref-code.model';

describe('Component Tests', () => {

    describe('HotlistRefCode Management Detail Component', () => {
        let comp: HotlistRefCodeDetailComponent;
        let fixture: ComponentFixture<HotlistRefCodeDetailComponent>;
        let service: HotlistRefCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApiTestTestModule],
                declarations: [HotlistRefCodeDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HotlistRefCodeService,
                    EventManager
                ]
            }).overrideTemplate(HotlistRefCodeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HotlistRefCodeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HotlistRefCodeService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new HotlistRefCode(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.hotlistRefCode).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
