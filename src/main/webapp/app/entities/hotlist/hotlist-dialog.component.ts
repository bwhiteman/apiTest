import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Hotlist } from './hotlist.model';
import { HotlistPopupService } from './hotlist-popup.service';
import { HotlistService } from './hotlist.service';
import { HotlistRefCode, HotlistRefCodeService } from '../hotlist-ref-code';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-hotlist-dialog',
    templateUrl: './hotlist-dialog.component.html'
})
export class HotlistDialogComponent implements OnInit {

    hotlist: Hotlist;
    authorities: any[];
    isSaving: boolean;

    hotlistrefcodes: HotlistRefCode[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private hotlistService: HotlistService,
        private hotlistRefCodeService: HotlistRefCodeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.hotlistRefCodeService.query()
            .subscribe((res: ResponseWrapper) => { this.hotlistrefcodes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hotlist.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hotlistService.update(this.hotlist), false);
        } else {
            this.subscribeToSaveResponse(
                this.hotlistService.create(this.hotlist), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Hotlist>, isCreated: boolean) {
        result.subscribe((res: Hotlist) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Hotlist, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'apiTestApp.hotlist.created'
            : 'apiTestApp.hotlist.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'hotlistListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackHotlistRefCodeById(index: number, item: HotlistRefCode) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hotlist-popup',
    template: ''
})
export class HotlistPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotlistPopupService: HotlistPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.hotlistPopupService
                    .open(HotlistDialogComponent, params['id']);
            } else {
                this.modalRef = this.hotlistPopupService
                    .open(HotlistDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
