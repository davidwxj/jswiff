/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.xml;

import com.jswiff.swfrecords.RegisterParam;
import com.jswiff.swfrecords.actions.Action;
import com.jswiff.swfrecords.actions.ActionBlock;
import com.jswiff.swfrecords.actions.ConstantPool;
import com.jswiff.swfrecords.actions.DefineFunction;
import com.jswiff.swfrecords.actions.DefineFunction2;
import com.jswiff.swfrecords.actions.GetURL;
import com.jswiff.swfrecords.actions.GetURL2;
import com.jswiff.swfrecords.actions.GoToFrame;
import com.jswiff.swfrecords.actions.GoToFrame2;
import com.jswiff.swfrecords.actions.GoToLabel;
import com.jswiff.swfrecords.actions.If;
import com.jswiff.swfrecords.actions.Jump;
import com.jswiff.swfrecords.actions.Push;
import com.jswiff.swfrecords.actions.SetTarget;
import com.jswiff.swfrecords.actions.StoreRegister;
import com.jswiff.swfrecords.actions.Try;
import com.jswiff.swfrecords.actions.UnknownAction;
import com.jswiff.swfrecords.actions.WaitForFrame;
import com.jswiff.swfrecords.actions.WaitForFrame2;
import com.jswiff.swfrecords.actions.With;
import com.jswiff.swfrecords.actions.Push.StackValue;
import com.jswiff.util.Base64;
import com.jswiff.util.StringUtilities;

import org.dom4j.Element;

import java.util.List;


/*
 * Writes SWF actions to XML.
 */
class ActionXMLWriter {
  
  static void writeAction(Element parentElement, Action action) {
    Element element = parentElement.addElement(action.actionType().toString());
    switch (action.actionType()) {      
      case CONSTANT_POOL:
        element = writeConstantPool(element, (ConstantPool) action);
        break;
      case DEFINE_FUNCTION:
        element = writeDefineFunction(element, (DefineFunction) action);
        break;
      case DEFINE_FUNCTION_2:
        element = writeDefineFunction2(element, (DefineFunction2) action);
        break;
      case GET_URL:
        element = writeGetURL(element, (GetURL) action);
        break;
      case GET_URL_2:
        element = writeGetURL2(element, (GetURL2) action);
        break;
      case GO_TO_FRAME:
        element = writeGoToFrame(element, (GoToFrame) action);
        break;
      case GO_TO_FRAME_2:
        element = writeGoToFrame2(element, (GoToFrame2) action);
        break;
      case GO_TO_LABEL:
        element = writeGoToLabel(element, (GoToLabel) action);
        break;
      case IF:
        element = writeIf(element, (If) action);
        break;
      case JUMP:
        element = writeJump(element, (Jump) action);
        break;
      case PUSH:
        element = writePush(element, (Push) action);
        break;
      case SET_TARGET:
        element = writeSetTarget(element, (SetTarget) action);
        break;
      case STORE_REGISTER:
        element = writeStoreRegister(element, (StoreRegister) action);
        break;
      case TRY:
        element = writeTry(element, (Try) action);
        break;
      case WAIT_FOR_FRAME:
        element = writeWaitForFrame(element, (WaitForFrame) action);
        break;
      case WAIT_FOR_FRAME_2:
        element = writeWaitForFrame2(element, (WaitForFrame2) action);
        break;
      case WITH:
        element = writeWith(element, (With) action);
        break;
      case UNKNOWN_ACTION:
        element = writeUnknown(element, (UnknownAction) action);
    }
    String label = action.getLabel();
    if (label != null) {
      element.addAttribute("label", label);
    }
  }

  private static Element writeConstantPool(Element element, ConstantPool constantPool) {
    List<String> constants = constantPool.getConstants();
    int id = 0;
    for (String constant : constants) {
      Element constantElement = element.addElement("constant");
      constantElement.addAttribute("id", Integer.toString(id++));
      if (StringUtilities.containsIllegalChars(constant)) {
        constantElement.addElement("value").addAttribute("base64", "true")
                       .addText(Base64.encodeString(constant));
      } else {
        constantElement.addElement("value").addText(constant);
      }
    }
    return element;
  }

  private static Element writeDefineFunction(Element element, DefineFunction defineFunction) {
    RecordXMLWriter.addAttributeWithCharCheck(element, "name", defineFunction.getName());
    String[] parameters       = defineFunction.getParameters();
    Element parametersElement = element.addElement("parameters");
    for (int i = 0; i < parameters.length; i++) {
      Element paramElement    = parametersElement.addElement("parameter");
      RecordXMLWriter.addAttributeWithCharCheck(paramElement, "name", parameters[i]);
    }
    RecordXMLWriter.writeActionBlock(element, defineFunction.getBody());
    return element;
  }

  private static Element writeDefineFunction2(Element element, DefineFunction2 defineFunction2) {
    RecordXMLWriter.addAttributeWithCharCheck(element, "name", defineFunction2.getName());
    element.addAttribute(
      "registercount", Short.toString(defineFunction2.getRegisterCount()));
    RegisterParam[] parameters = defineFunction2.getParameters();
    Element parametersElement  = element.addElement("parameters");
    for (int i = 0; i < parameters.length; i++) {
      RegisterParam parameter = parameters[i];
      Element paramElement    = parametersElement.addElement("registerparam");
      RecordXMLWriter.addAttributeWithCharCheck(paramElement, "name", parameter.getParamName());
      paramElement.addAttribute(
        "register", Short.toString(parameter.getRegister()));
    }
    Element preloadElement = element.addElement("preload");
    if (defineFunction2.preloadsArguments()) {
      preloadElement.addAttribute("arguments", "true");
    }
    if (defineFunction2.preloadsGlobal()) {
      preloadElement.addAttribute("global", "true");
    }
    if (defineFunction2.preloadsParent()) {
      preloadElement.addAttribute("parent", "true");
    }
    if (defineFunction2.preloadsRoot()) {
      preloadElement.addAttribute("root", "true");
    }
    if (defineFunction2.preloadsSuper()) {
      preloadElement.addAttribute("super", "true");
    }
    if (defineFunction2.preloadsThis()) {
      preloadElement.addAttribute("this", "true");
    }
    Element suppressElement = element.addElement("suppress");
    if (defineFunction2.suppressesArguments()) {
      suppressElement.addAttribute("arguments", "true");
    }
    if (defineFunction2.suppressesSuper()) {
      suppressElement.addAttribute("super", "true");
    }
    if (defineFunction2.suppressesThis()) {
      suppressElement.addAttribute("this", "true");
    }
    RecordXMLWriter.writeActionBlock(element, defineFunction2.getBody());
    return element;
  }

  private static Element writeGetURL(Element element, GetURL getURL) {
    RecordXMLWriter.addAttributeWithCharCheck(element, "url", getURL.getURL());
    RecordXMLWriter.addAttributeWithCharCheck(element, "target", getURL.getTarget());
    return element;
  }

  private static Element writeGetURL2(Element element, GetURL2 getURL2) {
    switch (getURL2.getSendVarsMethod()) {
      case GetURL2.METHOD_GET:
        element.addAttribute("sendvarsmethod", "get");
        break;
      case GetURL2.METHOD_POST:
        element.addAttribute("sendvarsmethod", "post");
        break;
      default:
        element.addAttribute("sendvarsmethod", "none");
    }
    if (getURL2.isLoadTarget()) {
      element.addAttribute("loadtarget", "true");
    }
    if (getURL2.isLoadVariables()) {
      element.addAttribute("loadvariables", "true");
    }
    return element;
  }

  private static Element writeGoToFrame(Element element, GoToFrame goToFrame) {
    element.addAttribute("frame", Integer.toString(goToFrame.getFrame()));
    return element;
  }

  private static Element writeGoToFrame2(Element element, GoToFrame2 goToFrame2) {
    element.addAttribute(
      "scenebias", Integer.toString(goToFrame2.getSceneBias()));
    if (goToFrame2.play()) {
      element.addAttribute("play", "true");
    }
    return element;
  }

  private static Element writeGoToLabel(Element element, GoToLabel goToLabel) {
    RecordXMLWriter.addAttributeWithCharCheck(element, "framelabel", goToLabel.getFrameLabel());
    return element;
  }

  private static Element writeIf(Element element, If ifAction) {
    String branchLabel = ifAction.getBranchLabel();
    RecordXMLWriter.addAttributeWithCharCheck(element, "branchlabel", branchLabel);
    if (ActionBlock.LABEL_OUT.equals(branchLabel)) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "offset", Short.toString(ifAction.getBranchOffset()));
    }
    return element;
  }

  private static Element writeJump(Element element, Jump jump) {
    String branchLabel = jump.getBranchLabel();
    RecordXMLWriter.addAttributeWithCharCheck(element, "branchlabel", branchLabel);
    if (ActionBlock.LABEL_OUT.equals(branchLabel)) {
      RecordXMLWriter.addAttributeWithCharCheck(element, "offset", Short.toString(jump.getBranchOffset()));
    }
    return element;
  }

  private static Element writePush(Element element, Push push) {
    List<StackValue> values = push.getValues();
    for (StackValue value : values) {
      Element pushEle = element.addElement(value.getType().toString());
      switch (value.getType()) {
        case CONSTANT_16:
        case CONSTANT_8:
          pushEle.addAttribute("id", value.valueString());
          break;
        case BOOLEAN:
        case DOUBLE:
        case FLOAT:
        case INTEGER:
          pushEle.addAttribute("value", value.valueString());
          break;
        case REGISTER:
          pushEle.addAttribute("number", value.valueString());
          break;
        case STRING:
          RecordXMLWriter.addAttributeWithCharCheck(pushEle, "value", value.valueString());
          break;
      }
    }
    return element;
  }

  private static Element writeSetTarget(Element element, SetTarget setTarget) {
    RecordXMLWriter.addAttributeWithCharCheck(element, "name", setTarget.getName());
    return element;
  }

  private static Element writeStoreRegister(Element element, StoreRegister storeRegister) {
    element.addAttribute("number", Short.toString(storeRegister.getNumber()));
    return element;
  }

  private static Element writeTry(Element element, Try tryAction) {
    if (tryAction.catchInRegister()) {
      element.addAttribute(
        "catchregister", Short.toString(tryAction.getCatchRegister()));
    } else {
      element.addAttribute("catchvariable", tryAction.getCatchVariable());
    }
    RecordXMLWriter.writeActionBlock(
      element.addElement("try"), tryAction.getTryBlock());
    if (tryAction.hasCatchBlock()) {
      RecordXMLWriter.writeActionBlock(
        element.addElement("catch"), tryAction.getCatchBlock());
    }
    if (tryAction.hasFinallyBlock()) {
      RecordXMLWriter.writeActionBlock(
        element.addElement("finally"), tryAction.getFinallyBlock());
    }
    return element;
  }

  private static Element writeUnknown(Element element, UnknownAction action) {
    element.addAttribute("code", Integer.toString(action.actionCode()));
    element.addText(Base64.encode(action.getData()));
    return element;
  }

  private static Element writeWaitForFrame(Element element, WaitForFrame waitForFrame) {
    element.addAttribute("frame", Integer.toString(waitForFrame.getFrame()));
    element.addAttribute(
      "skipcount", Short.toString(waitForFrame.getSkipCount()));
    return element;
  }

  private static Element writeWaitForFrame2(Element element, WaitForFrame2 waitForFrame2) {
    element.addAttribute(
      "skipcount", Short.toString(waitForFrame2.getSkipCount()));
    return element;
  }

  private static Element writeWith(Element element, With with) {
    RecordXMLWriter.writeActionBlock(element, with.getWithBlock());
    return element;
  }
}
