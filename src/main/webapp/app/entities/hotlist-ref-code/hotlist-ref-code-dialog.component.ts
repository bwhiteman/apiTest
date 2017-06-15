import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { HotlistRefCode } from './hotlist-ref-code.model';
import { HotlistRefCodePopupService } from './hotlist-ref-code-popup.service';
import { HotlistRefCodeService } from './hotlist-ref-code.service';

@Component({
    selector: 'jhi-hotlist-ref-code-dialog',
    templateUrl: './hotlist-ref-code-dialog.component.html'
})
export class HotlistRefCodeDialogComponent implements OnInit {

    hotlistRefCode: HotlistRefCode;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private hotlistRefCodeService: HotlistRefCodeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hotlistRefCode.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hotlistRefCodeService.update(this.hotlistRefCode), false);
        } else {
            this.subscribeToSaveResponse(
                this.hotlistRefCodeService.create(this.hotlistRefCode), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<HotlistRefCode>, isCreated: boolean) {
        result.subscribe((res: HotlistRefCode) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HotlistRefCode, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'apiTestApp.hotlistRefCode.created'
            : 'apiTestApp.hotlistRefCode.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'hotlistRefCodeListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-hotlist-ref-code-popup',
    template: ''
})
export class HotlistRefCodePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hotlistRefCodePopupService: HotlistRefCodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.hotlistRefCodePopupService
                    .open(HotlistRefCodeDialogComponent, params['id']);
            } else {
                this.modalRef = this.hotlistRefCodePopupService
                    .open(HotlistRefCodeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
